//
//  CharacterImageView.swift
//  iosApp
//
//  Created by Santiago Mattiauda on 24/4/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct CharacterImageView: View {
    
    let imageUrl:String
    
    var body: some View {
        AsyncImage(url: URL(string: imageUrl), transaction: Transaction(animation: .easeIn)) { phase in
            switch phase {
            case .empty:
                ZStack{
                    Color.gray.opacity(0.1)
                    ProgressView()
                }
            case .success(let image):
                image
                    .resizable()
                    .aspectRatio(0.67, contentMode: .fit)

            case .failure(_):
                Image(systemName: "exclamationmark.icloud")
                    .resizable()
                    .scaledToFit()

            @unknown default:
                Image(systemName: "exclamationmark.icloud")
            }
        }
        .frame(
            minWidth: 100,
            maxWidth: .infinity,
            minHeight: 200
        )
        .aspectRatio(0.67, contentMode: .fill)
        .cornerRadius(4)
    }
}

#Preview {
    CharacterImageView(imageUrl: "")
}
