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
```
## 🏗 Architecture Flow


Controller
↓
Service (Reactive)
↓
WebClient (Non-blocking)
↓
OpenAI API (Streaming)
↓
Flux<String>
↓
SSE Response
```