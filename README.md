## General Architecture / Overview
* The app is written in Kotlin in the MVVM architecture
* LiveData is used for viewmodels to hold data and propogate updates
  * Network calls are made via coroutines. Objects are wrapped in a `Resource` to show status
* The API is handled by Retrofit
  * `ApiInterface` defines the API calls. In `ApiBuilder` a retrofit instance is made using the interface
  * 
* When possible DataBindings are used to sync the UI with viewmodels
  * In many cases this means the value is completely defined in XML
  * There are some helper functions in `Utils`
  
## Connecting to Server
* The defaults should be good to go for connecting to the backend if it's running on the same machine
* Otherwise the dev menu (wrench in login/registration views) should allow you to change what you need
  * The mock server (`MockApi`) is set up to mostly returns constants
