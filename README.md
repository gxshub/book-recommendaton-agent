# Spring Boot LangChain4j: Book Recommendation Agent


(Linux/MacOS)
```shell
curl -G "http://localhost:8080/recommendation" \
--data-urlencode "sessionId=1" \
--data-urlencode "userMessage=Description: I would like to get a science fiction  for my 8 year-old boy."
```

(Windows CMD)
```shell
curl -G "http://localhost:8080/recommendation" ^
--data-urlencode "sessionId=1" ^
--data-urlencode "userMessage=Description: I would like to get a science fiction for my 8 year-old boy."
```

The user's input message is the content following `userMessage=`.
The agent will remember the recent dialogues per session defined by `sessionId`.
