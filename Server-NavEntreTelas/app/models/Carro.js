const mongoose = require('mongoose');
module.exports = function(){
   let schema = mongoose.Schema({
        nome: {
           type:"String",
           required: true
        },
        marca: {
           type: "String",
           required: true
        },
        placa: {
            type: "String",
            required: true
        },
        ano: {
            type: "String",
            required: true
        }
   });
   return mongoose.model("Carro", schema);
}();