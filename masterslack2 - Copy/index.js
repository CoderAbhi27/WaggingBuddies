const express = require('express');
const app = express();
const mongoose = require('mongoose');
const Pets = require('./models/pets');
const Shelter = require('./models/shelter');
const http = require('http').Server(app);
app.use(express.urlencoded({ extended: false }));
app.use(express.json());
mongoose.connect('mongodb+srv://aggarwalsiddharth49:wtpvkdyk9tu1PnlY@cluster0.fsxlufj.mongodb.net/?retryWrites=true&w=majority')
    .then(() => console.log('databse connected'))
    .catch(e => console.log(e));

app.post('/pets', async (req, res) => {
    try {
        const data = req.body;
        const pets = new Pets({ ...data });
        pets.petID = pets._id;
        const data2 = await pets.save();
        console.log(data2)
        res.send('Pet have been added');
    } catch (e) {
        res.send(e);
    }
});


app.delete('/pets/delete/:id', async (req, res) => {
    try {
        const { id } = req.params;
        const data = await Pets.findByIdAndDelete(id);
        console.log(data);
        res.send('Data has been deleted');
    } catch (e) {
        res.send(e);
    }

})

app.get('/pets', async (req, res) => {
    const data = await Pets.find();
    res.send(data);
});

app.post('/shelter', async (req, res) => {
    try {
        const data = req.body;
        const shelter = new Shelter({ ...data });
        shelter.shelterID = shelter._id;
        const data2 = await shelter.save();
        console.log(data2);
        res.send('New Shelter has been created');
    } catch (e) {
        res.send(e);
    }
});

app.post('/shelter/update/:id', async (req, res) => {

    try {
        const { id } = req.params;
        const { amount } = req.body;
        const data = await Shelter.findById(id);
        const value = { donationReceived: parseInt(data.donationReceived) + parseInt(amount) }
        const data2 = await Shelter.findByIdAndUpdate(id, value, { returnOriginal: false })
        console.log(data);
        console.log(value);
        console.log(data2);
        res.send("Thanks for donating");
    } catch (e) {
        res.send(e);
    }
});

app.get('/shelter', async (req, res) => {
    const data = await Shelter.find();
    res.send(data);
});

app.delete('/shelter/delete/:id', async (req, res) => {
    try {
        const { id } = req.params;
        const data = await Shelter.findByIdAndDelete(id);
        console.log(data);
        res.send('Shelter has been created');
    } catch (e) {
        console.log(e);
        res.send(e)
    }
});


app.get('/', (req, res) => {
    res.send('on home page');
})

http.listen(5000, () => {
    console.log('connected to port 5000');
})