import SwiftUI
import Shared

struct ContentView: View {
    
    @State var viewModel: CharactersViewModel
    
    @State var characters: [Character] = []
    
    private let columns: [GridItem] =
      Array(repeating: .init(.flexible(), spacing: 20, alignment: .center), count: 2)
    
    var body: some View {
        NavigationView{
            ScrollView{
                LazyVGrid(columns: columns, spacing: 20){
                    ForEach(characters, id: \.id){ item in
                        ZStack(alignment: .bottomTrailing) {
                              CharacterImageView(imageUrl: item.image)

                            Button(action: {viewModel.addToFavorites(character: item)}) {
                                  Image(systemName: item.isFavorite ? "heart.fill" : "heart")
                                      .foregroundColor(item.isFavorite ? .red : .white)
                                      .padding()
                              }
                        }
                  
                    }
                }
            }
            .task {
                for await data in viewModel.characters {
                    characters = data 
                }
            }
            .padding()
            .navigationTitle("Rick and Morty")
            .toolbar{
                let favoriteViewModel = Injector.shared.provideFavoritesViewModel()
                NavigationLink(destination: FavoriteView(viewModel: favoriteViewModel)) {
                            Image(systemName: "heart.fill" )
                                .foregroundColor(.black)
                                .padding()
                        }.isDetailLink(false)
            }
        }
    }
}

//struct ContentView_Previews: PreviewProvider {
//    static var previews: some View {
//        ContentView(viewModel:  )
//    }
//}
