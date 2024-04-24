import SwiftUI
import Shared
@main
struct iOSApp: App {
    
    init(){
        LibraryInitializer().doInit(config: PlatformConfig())
    }
    
	var body: some Scene {
		WindowGroup {
            let viewModel = Injector.shared.provideCharacterViewModel()
			ContentView(viewModel: viewModel)
		}
	}
}
