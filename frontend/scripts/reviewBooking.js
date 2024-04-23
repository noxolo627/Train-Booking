document.addEventListener('DOMContentLoaded',()=>{
const fullname = document.getElementById('review-fullname');
const age = document.getElementById('review-age');
const seatType = document.getElementById('review-seat-type');
const trainClass = document.getElementById('review-train-class');
const location = document.getElementById('review-location');
const destination = document.getElementById('review-destination');
const travelDate = document.getElementById('review-travel-date');


fullname.value = localStorage.getItem('fullname');
age.value = localStorage.getItem('age');
seatType.value = localStorage.getItem('seatType');
trainClass.value =localStorage.getItem('trainClass');
location.value = localStorage.getItem('fromDropdownValue');
destination.value = localStorage.getItem('toDropdownValue');
travelDate.value = localStorage.getItem('travelDataValue');

});
