                ┌────────────────────┐
                │      Client        │
                │  Postman / UI App  │
                └─────────┬──────────┘
                          │ HTTP POST
                          ▼
                ┌────────────────────┐
                │   ChatController   │
                │  REST Endpoint     │
                └─────────┬──────────┘
                          │ calls
                          ▼
                ┌────────────────────┐
                │    ChatService     │
                │ Business Logic     │
                └─────────┬──────────┘
                          │ uses
                          ▼
                ┌────────────────────┐
                │      AiClient      │
                │ Interface Layer    │
                └─────────┬──────────┘
                          │ implemented by
                          ▼
                ┌────────────────────┐
                │   AiClientImpl     │
                │ HTTP Call Layer    │
                └─────────┬──────────┘
                          │ REST Call
                          ▼
                ┌────────────────────┐
                │   AI Provider API  │
                │  (External Model)  │
                └────────────────────┘
