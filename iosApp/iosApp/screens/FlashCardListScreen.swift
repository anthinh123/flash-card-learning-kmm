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
import SwiftUIPager

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
    @StateObject var page: Page = .first()
    @State private var showAddingFlashCardScreen = false
    
    var body: some View {
        NavigationStack {
            ZStack {
                if (!viewModel.uiState.flashCards.isEmpty){
                    VStack {
                        Pager(page: page, data: viewModel.uiState.flashCards, id: \.self.id, content: { flashCardPo in
                            let displayText = flashCardPo.isDisplayMeaning ? flashCardPo.meaning : flashCardPo.original
                            CardView(text: displayText)
                                .padding(.horizontal, 32)
                                .padding(.vertical, 32)
                        })
                        
                        UsefulView(
                            clickToDone: {},
                            clickToSpeaker: {},
                            clickToTranslate: {
                                viewModel.flashCardListViewModel.event(event:Event.OnTranslateIconSelected(id:viewModel.uiState.flashCards[page.index].id))
                            }
                        )
                    }
                } else {
                    let _ = print("EMPTY")
                }
                
                if (viewModel.uiState.isLoading){
                    Loader()
                }
                
                if (!viewModel.uiState.error.isEmpty){
                    ErrorMessage(message: viewModel.uiState.error)
                }
                
            }.onAppear{
                self.viewModel.startObserving()
            }
            .navigationBarTitle("Flash Card", displayMode: .inline)
            .toolbar {
                ToolbarItem(placement: .navigationBarTrailing,
                            content: { Button(action: { showAddingFlashCardScreen = true },
                                              label: { Image(systemName: "plus") }) })
            }
            .navigationDestination(isPresented: $showAddingFlashCardScreen) {
                AddingFlashCardScreen(viewModel: .init()).toolbarRole(.editor)
            }
        }
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
        ZStack() {
            RoundedRectangle(cornerRadius: 25, style: .continuous)
                .fill(.mint)
            
            GeometryReader { geometry in
                ScrollView(.vertical) {
                    VStack {
                        Text(text)
                            .font(.largeTitle)
                            .frame(maxWidth: 400)
                            .multilineTextAlignment(.center)
                    }
                    .padding()
                    .frame(width: geometry.size.width)      // Make the scroll view full-width
                    .frame(minHeight: geometry.size.height) // Set the content’s min height to the parent
                }
            }
        }
    }
}

struct UsefulView : View {
    let clickToDone : () -> Void
    let clickToSpeaker : () -> Void
    let clickToTranslate : () -> Void
    
    var body: some View {
        HStack (alignment: .center, spacing: 10){
            Button() {
                self.clickToDone()
            } label: {
                Image(systemName: "checkmark")
                    .resizable()
                    .frame(width: 40.0, height: 40.0)
                    .foregroundColor(.black)
                    .padding(.horizontal, 20)
                    .padding(.vertical, 8)
            }.frame(maxWidth: .infinity)
            
            Button() {
                self.clickToSpeaker()
            } label: {
                Image(systemName: "speaker.wave.2.fill")
                    .resizable()
                    .frame(width: 40.0, height: 40.0)
                    .foregroundColor(.black)
                    .padding(.horizontal, 20)
                    .padding(.vertical, 8)
            }.frame(maxWidth: .infinity)
            
            Button() {
                self.clickToTranslate()
            } label: {
                Image(systemName: "rectangle.2.swap")
                    .resizable()
                    .frame(width: 40.0, height: 40.0)
                    .foregroundColor(.black)
                    .padding(.horizontal, 20)
                    .padding(.vertical, 8)
            }.frame(maxWidth: .infinity)
        }
        .padding(.horizontal, 20)
        .padding(.bottom, 12)
    }
}


#Preview {
    FlashCardListScreen(viewModel: .init())
}
