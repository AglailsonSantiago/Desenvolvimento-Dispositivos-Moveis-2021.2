// Importando módulo express(Padrão CommonJS)
const express = require('express');
const routerCarros = require("../app/routes/CarroRouter");
const bodyParser = require("body-parser");

// Exportando módulo (Padrão CommonJS)
module.exports = function() {
    let app = express ();
    //Definindo variável de aplicação
    app.set('port', 8393);
    app.use(bodyParser.json());
    app.use(bodyParser.urlencoded({extended: false}));
    app.use(express.static('./public'));

    routerCarros(app);

    return app;
};