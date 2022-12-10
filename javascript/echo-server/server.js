// A simple echo server, made for testing that dynamically generated query
// params are coming through as intended.
//
// Install express with `npm install express`, run with `node server.js`, then
// hit the server with a request like:
// http://192.168.1.101:3000/echo?name=me&msg=hi

const express = require('express');
const app = express();

app.get('/echo', (req, res) => {
    res.json(req.query);
});

app.listen(3000, () => {
    console.log('Echo server listening on port 3000!');
});
