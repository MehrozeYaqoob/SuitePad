- Presentation module sends a broadcast from shouldInterceptRequest method, to request data from data source which routes through proxy module.

- Proxy Module receives that Explicit broadcast with intent data and requests Datasource to provide data by sending another explicit broadcast

- Datasource receives that broadcast and starts a JobIntentService to fetch data from Room database. Once data is fetched, it will send that data in JSON string to proxy module via explicit broadcast

- Proxy Module receives that broadcast from datasource with Menu Item data as JSON in intent and triggers an action with data which is listened by Presentation Module

- Presentation module will receive that data and web view will render it. 


Bug: Currently web view loads the data but doesn’t invalidate view. You would be required to re-run and data will be visible.

Info: 
- Unit tests are written in each module
- MVVM architecture pattern is used
- Room DB can be replaced by Content Providers
- Proxy can become a service rather than Broadcast
- Constants are kept in separate library module named as Constants-app
- Broadcast are tried to made secure by using them explicitly but they can be made more secure