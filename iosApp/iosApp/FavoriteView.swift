//
//  FavoriteView.swift
//  iosApp
//
//  Created by Santiago Mattiauda on 15/5/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct FavoriteView: View {
    @State var viewModel: FavoritesViewModel
    
    @State var characters: [Character] = []
   
    var body: some View {
        ScrollView{
            LazyVStack{
                ForEach(characters, id: \.id){ item in
                    HStack(alignment:.center){
                        CharacterImageView(imageUrl: item.image)
                            .frame(width: 80, height: 80)
                               .clipShape(Circle())
                               .overlay(Circle().stroke(Color.white, lineWidth: 2))
                        Text(item.name)
                        Spacer()
                        Button(action: {viewModel.addToFavorites(character: item)}) {
                              Image(systemName: item.isFavorite ? "heart.fill" : "heart")
                                  .foregroundColor(item.isFavorite ? .red : .white)
                          }
                    }
              
                }
            }
        }
        .task {
            for await data in viewModel.characters {
                characters = data
            }
        }.padding()
        .navigationBarTitleDisplayMode(.inline)
    }
}

//#Preview {
//    FavoriteView()
//}
