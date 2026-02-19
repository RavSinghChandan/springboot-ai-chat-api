# ❌ Mistakes & Debug Log — Project 1 (AI Chat API)

This file documents real errors encountered while building the project and what caused them.
Purpose: track mistakes → understand root cause → avoid repeating them.

---

## 1️⃣ Lombok Not Generating Getters

### Error

```
cannot find symbol method getMessage()
```

### Cause

Lombok annotations were present but annotation processing was not enabled in IDE.

### Fix

Either:

* Enable annotation processing in IDE

OR

* Replace Lombok DTO with manual getters

### Lesson

Never assume Lombok works automatically. Always verify IDE config.

---

## 2️⃣ Constructor Not Found

### Error

```
constructor ChatResponse cannot be applied
required: no arguments
found: String
```

### Cause

@AllArgsConstructor not generated due to Lombok issue.

### Fix

Manually wrote constructor.

### Lesson

Generated code dependencies can break silently. Understand Java fundamentals first.

---

## 3️⃣ Spring Bean Not Found

### Error

```
No qualifying bean of type 'AiClient'
```

### Cause

Spring could not find implementation class for interface injection.

### Fix

Added annotation:

```
@Component
```

to implementation class.

### Lesson

Spring only injects beans that are registered in context.

---

## 4️⃣ Dependency Injection Failure

### Root Cause Pattern

Whenever Spring fails to start:

```
APPLICATION FAILED TO START
```

The real error is always below this line.

### Lesson

Ignore log noise. Scroll to bottom first.


---

## 📌 Personal Rule

Before asking for help:

1. Read error message
2. Identify class
3. Identify dependency
4. Check annotations
5. Check package structure

---

✔ Status: Project successfully runs after fixes.
