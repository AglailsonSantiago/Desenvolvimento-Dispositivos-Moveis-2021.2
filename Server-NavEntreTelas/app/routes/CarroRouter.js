const controller = require("../controllers/CarroController");

module.exports = function(app){

    app.get("/carros", controller.listarCarros);
    app.get("/carros/:id", controller.buscarCarroPorId);
    app.post("/carros", controller.inserirCarro)
    app.delete("/carros/:id", controller.removerCarro);

}