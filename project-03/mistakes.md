Excellent.

This is the most powerful document for interviews.

Senior engineers stand out because they can articulate:

> What broke
> Why it broke
> How they fixed it
> What principle they learned

Copy this into:

```id="d3m6kq"
project-3/mistakes.md
```

---

# 🧨 mistakes.md

# Project 3 — AI Smart Log Analyzer

## Engineering Failures, Debugging, and Lessons

---

This document captures the real mistakes made during Project 3 and the engineering lessons extracted from them.

This is not about perfection.

This is about growth.

---

# ❌ Mistake 1 — Sending Raw Logs Directly to AI

### What I Did

Initially, I sent the entire uploaded log file directly to the AI API without preprocessing.

### What Happened

* High token usage
* Slow response time
* Expensive API calls
* Noisy output
* Weak reasoning from AI

### Why It Failed

LLMs are sensitive to context quality.
Raw logs contain:

* Repeated lines
* Stack traces
* Debug noise
* Irrelevant metadata

This reduced signal-to-noise ratio.

### What I Learned

AI reasoning quality depends heavily on input conditioning.

Preprocessing is mandatory.

### Engineering Principle Learned

> Data conditioning is part of system design, not an optimization.

---

# ❌ Mistake 2 — Trusting AI Output Format Blindly

### What I Did

I assumed the AI would always return valid JSON because I asked it to.

### What Happened

* Occasional malformed JSON
* Missing fields
* Parsing failures
* Runtime exceptions

### Why It Failed

LLMs are probabilistic systems.
They may:

* Add extra text
* Miss quotation marks
* Change structure slightly

### What I Learned

Never trust AI output blindly.

Always:

* Parse safely
* Validate structure
* Add fallback logic

### Engineering Principle Learned

> External systems must be validated, not trusted.

---

# ❌ Mistake 3 — No Token Control Initially

### What I Did

Did not limit input size in early implementation.

### What Happened

* Large log file caused:

    * High latency
    * Increased cost
    * Possible timeout

### Why It Failed

AI pricing is token-based.

Large logs → exponential cost growth.

### What I Learned

AI systems must include cost-aware architecture.

Token truncation is not optional.

### Engineering Principle Learned

> AI engineering includes financial engineering.

---

# ❌ Mistake 4 — No Hybrid Severity Logic

### What I Did

Initially allowed AI to classify severity entirely.

### What Happened

* AI misclassified obvious CRITICAL errors
* Inconsistent severity levels

### Why It Failed

AI does pattern inference, not deterministic evaluation.

Some errors (OutOfMemoryError, DB failure) are always critical.

### What I Learned

Pure AI systems are unstable.

Hybrid systems are safer.

### Engineering Principle Learned

> Deterministic rules should handle deterministic cases.

---

# ❌ Mistake 5 — Blocking Call Inside Reactive Flow

### What I Did (Earlier Version)

Used:

```java
.block()
```

inside reactive pipeline.

### What Happened

* Broke non-blocking design
* Reduced scalability
* Violated reactive principles

### Why It Failed

Blocking defeats purpose of WebFlux.

### What I Learned

Reactive systems must remain non-blocking end-to-end.

### Engineering Principle Learned

> Mixing blocking and reactive models leads to hidden performance issues.

---

# ❌ Mistake 6 — Weak Error Handling Around File Upload

### What I Did

Did not validate file type and size initially.

### What Happened

* Incorrect content-type caused:

    * MissingServletRequestPartException
    * HttpMediaTypeNotSupportedException

### Why It Failed

Multipart handling requires correct client configuration.

Backend must validate input strictly.

### What I Learned

File upload endpoints must:

* Enforce content type
* Enforce size limit
* Validate extension

### Engineering Principle Learned

> Input validation is security.

---

# ❌ Mistake 7 — No Observability at First

### What I Did

Initially had no latency tracking or failure counters.

### What Happened

* Could not measure:

    * Response time
    * Failure rate
    * Performance trends

### Why It Failed

Without metrics, system behavior is invisible.

### What I Learned

Observability must be built in, not added later.

### Engineering Principle Learned

> If you can’t measure it, you can’t scale it.

---

# ❌ Mistake 8 — Underestimating AI Latency

### What I Assumed

AI would respond quickly.

### What Happened

* Timeout errors
* AsyncRequestTimeoutException
* Slow large-log responses

### Why It Failed

LLMs are network-dependent and computationally heavy.

### What I Learned

AI calls must include:

* Timeout control
* Circuit breaker
* Fallback response

### Engineering Principle Learned

> External AI services are high-latency dependencies.

---

# ❌ Mistake 9 — Treating AI as Feature Instead of Infrastructure

### What I Did Initially

Thought AI was just another feature in controller.

### What I Realized

AI requires:

* Resilience patterns
* Cost awareness
* Observability
* Validation
* Hybrid logic

### Engineering Principle Learned

> AI is infrastructure, not just functionality.

---

# 🧠 Biggest Mindset Shift

Before Project 3:

“I can upload file and get AI summary.”

After Project 3:

“I can engineer a resilient, hybrid, cost-controlled AI-powered backend system.”

---

# 🎯 Interview-Ready Reflection

If asked:

“What mistakes did you make while building AI Log Analyzer?”

You can confidently say:

* Initially sent raw logs → learned importance of data conditioning
* Trusted AI output blindly → implemented validation
* Ignored token cost → added truncation
* Used blocking call → corrected to reactive
* Relied fully on AI → added hybrid rule-based logic
* Missed observability → added metrics

This shows maturity.

---

# 🏁 Final Takeaway

Mistakes transformed this from:

“AI experiment”

into

“Production-ready AI system design.”

---

