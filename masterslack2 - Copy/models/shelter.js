const mongoose=require('mongoose');

const shelterSchema= new mongoose.Schema({
    name:String,
    address:String,
    totalCapacity :[Number],
    currentStrength: [Number],
    donationReceived:Number,
    shelterID : String
})

module.exports= mongoose.model('Shelter',shelterSchema);    