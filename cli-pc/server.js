var express = require('express')
var app = express()
app.use(express.static("./static/nav/")).listen(8060);

