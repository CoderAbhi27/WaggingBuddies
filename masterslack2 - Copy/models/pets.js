const mongoose =require('mongoose');

const petsSchema= new mongoose.Schema({
    petType:String,
    petName:String,
    petAge:Number,
    petBreed:String,
    petOwnersName:String,
    petOwnerEmail:String, 
    petHealth:Number,
    petAddress:String,
    petImageURL:String,
    petAdoptionTime:Number,
    adoptionMsg:String,
    gender:Boolean,
    petID:String
});

module.exports= mongoose.model('Pets' , petsSchema);