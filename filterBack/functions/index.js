const functions = require('firebase-functions');

var admin = require("firebase-admin");

var serviceAccount = require("./serviceAccount.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://familytree-15872.firebaseio.com"
});

const db = admin.firestore();
db.settings({ ignoreUndefinedProperties: true })

var matches = require("./matches.json");

exports.addMatches = functions.https.onRequest((request,response)=>{
    addMatches(request,response);
});

exports.removeAll = functions.https.onRequest((request,response)=>{
    removeAll(request,response);
});

exports.getMatches = functions.https.onRequest((request,response)=>{
    getAll(request,response);
});

addMatches = (req,res) => {
    matches.forEach(function(obj) {
        db.collection("matches").add({
            display_name: obj.display_name,
            age: obj.age,
            job_title: obj.job_title,
            height_in_cm: obj.height_in_cm,
            main_photo: obj.main_photo,
            city: obj.city,
            compatibility_score: obj.compatibility_score,
            contacts_exchanged: obj.contacts_exchanged,
            favourite: obj.favourite,
            religion: obj.religion
        }).then(function(docRef) {
            console.log("Document written with ID: ", docRef.id);
            res.send();
        })
        .catch(function(error) {
            console.error("Error adding document: ", error);
            res.status(400).send(error);
        });
    });
}

removeAll = async (req,res) => {
    const del = await db.collection('matches').delete();
    res.send();
}

getAll = async (req,res) => {
    const mathcesRef = db.collection('matches');
    const snapshot = await mathcesRef.get();
    var list = snapshot.docs.map(doc => doc.data());
    var hasAvatar = req.body.has_avatar;
    var hasContacts = req.body.has_contacts;
    var favourite = req.body.favourite;
    var compatibilityScore = req.body.compatibility_score;
    var age = req.body.age;
    var height = req.body.height;
    if(hasAvatar != undefined) list = list.filter(doc => hasAvatar ? doc.main_photo != undefined : doc.main_photo == undefined );
    if(hasContacts != undefined) list = list.filter(doc => hasContacts ? doc.contacts_exchanged != 0 : doc.contacts_exchanged == 0);
    if(favourite != undefined) list = list.filter(doc => favourite ? doc.favourite : !doc.favourite);
    if(compatibilityScore != undefined) list = list.filter(doc => doc.compatibility_score >= compatibilityScore.min*0.01 && doc.compatibility_score <= compatibilityScore.max*0.01);
    if(age != undefined) list = list.filter(doc => doc.age >= age.min && doc.age <= age.max);
    if(height != undefined) list = list.filter(doc => doc.height_in_cm >= height.min && doc.height_in_cm <= height.max);
    res.send(list);
}


