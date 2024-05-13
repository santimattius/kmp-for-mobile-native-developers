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
                        CharacterImageView(imageUrl: item.image)
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
        }
    }
}

//struct ContentView_Previews: PreviewProvider {
//    static var previews: some View {
//        ContentView(viewModel:  )
//    }
//}
