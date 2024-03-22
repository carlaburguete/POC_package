// swift-tools-version: 5.7
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "POC_package",
    products: [
        // Products define the executables and libraries a package produces, and make them visible to other packages.
        .library(
            name: "Security Utils",
            targets: ["SecurityUtilsTarget"]),
        .library(
            name: "UI Components",
            targets: ["UIComponentsTarget"])
    ],
    dependencies: [
        // Dependencies declare other packages that this package depends on.
        // .package(url: /* package url */, from: "1.0.0"),
    ],
    targets: [
        // Targets are the basic building blocks of a package. A target can define a module or a test suite.
        // Targets can depend on other targets in this package, and on products in packages this package depends on.
        .target(
            name: "SecurityUtilsTarget",
            dependencies: [],
            path: "Security Utils/iOS"
        ),
        .target(
            name: "UIComponentsTarget",
            dependencies: [],
            path: "UI Components/iOS"
        )
    ]
)
