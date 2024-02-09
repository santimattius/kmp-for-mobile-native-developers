import SwiftUI
import Shared

class SwiftGameRemoteDataSources: GameRemoteDataSources{
    
    func getGames() async throws -> Any? {
        
        guard let url = URL(string: "https://www.freetogame.com/api/games") else {
            throw GameServiceError.invalidURL
        }
        let (data, _) = try await URLSession.shared.data(from: url)
        let result = try JSONDecoder().decode(GameResponse.self, from: data)
        return result.map{ item in item.asDomainModel() }
    }
    
}

@Observable
class GameViewModel{
    
//    let repository = GameRepository(remoteDataSources:  Datasource_iosKt.provideGameDataSource()
    let repository = GameRepository(remoteDataSources: SwiftGameRemoteDataSources())
    
    var data:String = ""
    
    func load(){
        self.repository.fetch(completionHandler:{response,_ in
            self.data = "\(String(describing: response))"
        })
    }
}

struct ContentView: View {
    @State var viewModel = GameViewModel()
    
    var body: some View {
        VStack {
            Image(systemName: "swift")
                .imageScale(.large)
                .foregroundColor(.accentColor)
            Text("SwiftUI: \(Greeting().greet()) \(viewModel.data)").font(/*@START_MENU_TOKEN@*/.title/*@END_MENU_TOKEN@*/)
            
        }.onAppear(perform: {
            viewModel.load()
        })
        .padding()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
