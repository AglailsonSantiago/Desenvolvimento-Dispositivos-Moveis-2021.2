function render(carro){
    return{
        id : carro._id,
        nome : carro.nome,
        marca : carro.marca,
        placa : carro.placa,
        ano : carro.ano
    }
}

module.exports.render = render;

function renderMany(carros){
    return carros.map(render);
}

module.exports.renderMany = renderMany;