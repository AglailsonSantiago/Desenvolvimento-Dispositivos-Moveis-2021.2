const Carro= require("../models/Carro");
const carroView = require("../views/CarroView");

module.exports.listarCarros = function(req, res){
    let promisse = Carro.find().exec();
    promisse.then(function(carros){
        res.status(200).json(carroView.renderMany(carros));
    }).catch(function(error){
        res.status(500).json({mensagem:"Requisição falhou!"})
    })
}

module.exports.buscarCarroPorId = function(req, res){
    let id = req.params.id;
    let promisse = Usuario.findById(id).exec();
    promisse.then(function(carro){
        res.status(200).json(userView.render(carro));
    }).catch(function(error){
        res.status(400).json({mensagem: "Carro não encontrado!"});
    });
}

module.exports.inserirCarro = function(req, res){
    let carro = req.body;
    let promisse = Carro.create(carro);

    promisse.then(function(carro){
        res.status(201).json(carroView.render(carro));
    }).catch(function(error){
        res.status(400).json({mensagem: "Requisição falhou!"});
    })
}

module.exports.removerCarro = function(req, res){
    let id = req.params.id;
    let promisse = Carro.findByIdAndDelete(id);
    promisse.then(function(){
        res.status(200).json({mensagem: "Carro deletado com sucesso!"});
    }).catch(function(error){
        res.status(400).json({mensagem: "Carro não encontrado!"});
    })
}
