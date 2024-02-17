//
//  FlashCardListScreen.swift
//  iosApp
//
//  Created by Thinh An on 14/02/2024.
//  Copyright © 2024 orgName. All rights reserved.
//
//
import SwiftUI
import shared

extension FlashCardListScreen {
    @MainActor
    class FlashCardListViewModelWrapper : ObservableObject {
        let flashCardListViewModel : FlashCardListViewModel
        
        init() {
            flashCardListViewModel = FlashCardLearningInjector().flashCardListViewModel
            uiState = flashCardListViewModel.flashCardListState.value
        }
        
        @Published var uiState : FlashCardListState
        
        func startObserving() {
            Task {
                for await _uiState in flashCardListViewModel.flashCardListState {
                    self.uiState = _uiState
                }
            }
        }
    }
}

struct FlashCardListScreen: View {
    @ObservedObject private(set) var viewModel : FlashCardListViewModelWrapper
    
    var body: some View {
        VStack {
            AppBar()
            
            if (viewModel.uiState.isLoading){
                Loader()
            }
            
            if (!viewModel.uiState.error.isEmpty){
                ErrorMessage(message: viewModel.uiState.error)
            }
            
            if (!viewModel.uiState.flashCards.isEmpty){
                let _ = print(" size = \(viewModel.uiState.flashCards.count)")
                //                ScrollView(.horizontal) {
                //                    LazyHStack(spacing: 32) {
                //                        ForEach(viewModel.uiState.flashCards, id: \.self.id) { flashCardPo in
                //                            CardView(text: flashCardPo.original)
                //                        }
                //                    }
                //                }
                
                TabView{
                    ForEach(viewModel.uiState.flashCards, id: \.self.id) { flashCardPo in
                        CardView(text: flashCardPo.original)
                    }
                }.tabViewStyle(.page(indexDisplayMode: .never))
                .indexViewStyle(.page(backgroundDisplayMode: .always))
            } else {
                let _ = print(" EMPTY")
            }
            
        }.onAppear{
            self.viewModel.startObserving()
        }
    }
}

struct AppBar: View {
    var body: some View {
        Text("Flash Card")
            .font(.largeTitle)
            .fontWeight(.bold)
    }
}

struct ErrorMessage: View {
    var message: String
    
    var body: some View {
        Text(message)
            .font(.title)
    }
}

struct Loader: View {
    var body: some View {
        ProgressView()
    }
}

struct CardView : View {
    let text : String
    
    var body: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 25, style: .continuous)
                .fill(.mint)
            
            VStack {
                Text(text)
                    .font(.largeTitle)
                    .foregroundColor(.black)
            }
            .padding(20)
            .multilineTextAlignment(.center)
        }
        .frame(width: UIScreen.main.bounds.size.width - 32, height: .infinity)
        .padding(.vertical, 32)
    }
}


#Preview {
    FlashCardListScreen(viewModel: .init())
}
