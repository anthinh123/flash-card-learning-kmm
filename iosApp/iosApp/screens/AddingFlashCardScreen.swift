//
//  AddingFlashCardScreen.swift
//  iosApp
//
//  Created by Thinh An on 18/02/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

extension AddingFlashCardScreen {
    @MainActor
    class AddingFlashCardViewModelWrapper : ObservableObject {
        let addingFlashCardViewModel : AddingFlashCardViewModel
        
        init(){
            addingFlashCardViewModel = FlashCardLearningInjector().addingFlashCardViewModel
            uiStateScreen = addingFlashCardViewModel.uiState.value
        }
        
        @Published var uiStateScreen : AddingFlashCardContactAddingFlashCardUiState
        
        func startObserving(){
            Task {
                for await _uiState in addingFlashCardViewModel.uiState {
                    self.uiStateScreen = _uiState
                }
            }
        }
        
    }
}

struct AddingFlashCardScreen: View {
    @ObservedObject private(set) var viewModel : AddingFlashCardViewModelWrapper
    @State private var originalText: String = ""
    @State private var meaningText: String = ""
    @Environment(\.presentationMode) var presentationMode
    
    var body: some View {
        NavigationStack {
            ZStack{
                VStack{
                    HStack {
                        Text("Sentence").font(.title2)
                        
                        Button() {
                            print("Camera clicked")
                        } label: {
                            Image(systemName: "camera.fill")
                                .foregroundColor(.primary)
                                .padding(.horizontal, 20)
                                .padding(.vertical, 8)
                        }.frame(minWidth: 24)
                        
                        Button() {
                            print("Mic clicked")
                        } label: {
                            Image(systemName: "mic.fill")
                                .foregroundColor(.primary)
                                .padding(.horizontal, 20)
                                .padding(.vertical, 8)
                        }.frame(minWidth: 24)
                    }
                    
                    TextEditor(text: $originalText)
                                .foregroundColor(Color.gray)
                                .font(.custom("HelveticaNeue", size: 13))
                                .lineSpacing(5)
                    
                    HStack {
                        Text("Meaning").font(.title2)
                        
                        Button() {
                            print("meaning Camera clicked")
                        } label: {
                            Image(systemName: "camera.fill")
                                .foregroundColor(.primary)
                                .padding(.horizontal, 20)
                                .padding(.vertical, 8)
                        }.frame(minWidth: 24)
                        
                        Button() {
                            print("meaning Mic clicked")
                        } label: {
                            Image(systemName: "mic.fill")
                                .foregroundColor(.primary)
                                .padding(.horizontal, 20)
                                .padding(.vertical, 8)
                        }.frame(minWidth: 24)
                    }
                    
                    TextEditor(text: $meaningText)
                                .foregroundColor(Color.gray)
                                .font(.custom("HelveticaNeue", size: 13))
                                .lineSpacing(5)
                }
                
            }.onAppear{
                viewModel.startObserving()
            }
            .navigationBarTitle("Add new phrase", displayMode: .inline)
            .toolbar {
                ToolbarItem(
                    placement: .navigationBarTrailing,
                    content: {
                        Button(
                            action: {
                                self.viewModel.addingFlashCardViewModel.event(event: AddingFlashCardContactEvent.SetOriginalContent(original: originalText))
                                viewModel.addingFlashCardViewModel.event(event: AddingFlashCardContactEvent.SetMeaningContent(meaning: meaningText))
                                self.viewModel.addingFlashCardViewModel.event(event: AddingFlashCardContactEvent.OnSave())
                                self.presentationMode.wrappedValue.dismiss()
                            },
                            label: { Image(systemName: "checkmark")}
                        )
                    }
                )
            }
        }
    }
}

#Preview {
    AddingFlashCardScreen(viewModel: .init())
}
