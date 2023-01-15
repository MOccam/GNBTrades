# GNBTrades

## Features

Its features are:

- SOLID principles have been taken into account
- MVVM
- Dependency injection with Koin
- Unit testing

Has been used
- Coroutines
- Flow
- View Binding (_Android Jetpack_)
- Navigation (_Android Jetpack_)
- Retrofit
- Koin

Third party libraries:
- [Retrofit](https://square.github.io/retrofit/)
- [Koin](https://insert-koin.io/)

An extra module (own authorship) called "_core_" is used. It is used for various purposes:
- Establishment and processing of the HTTP connection.
- Common functions abstracted from business logic

## Architecture

Has been followed the *SOLID principles* and *Clean architecture* and the [architecture pattern recommended by Google](https://developer.android.com/jetpack/guide) has been followed.

### UI layer
Layer dedicated to display the application data on the screen. This is integrated into the "_app_" layer.

### Domain layer
Layer is responsible for encapsulating complex business that can be reused multiple ViewModels. This is integrated into the "_domain_" layer.

### Data layer
This layer is responsible for data recovery. This layer does not contain complex business logic, rather than the communication itself for data recovery. This is integrated into the "_data_" layer.