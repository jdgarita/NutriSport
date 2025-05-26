import SwiftUI
import GoogleSignIn

@main
struct iOSApp: App {
    var body: some Scene {
        WindowGroup {
            ContentView().onOpenURL(
                perform: { url in
                            GIDSignIn.sharedInstance.handle(url)
                        }
            )
        }
    }
}

class AppDelegate: NSObject, UIApplicationDelegate {
    func application(
        _ application: UIApplication,
        open url: URL,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]? = nil
    ) -> Bool {
        var handled: Bool
        handled = GIDSignIn.sharedInstance.handle(url)
        if handled {
            return true
        }
        
        return false
    }
}
