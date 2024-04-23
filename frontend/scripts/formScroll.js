
allaboard_url = "http://localhost:5000";

async function apiGet(endpoint) {
  return await fetch(allaboard_url + endpoint, {
    method: "GET",
    headers: {
      'Content-type': 'application/json; charset=UTF-8',
      'Authorization': 'Bearer ' + 'eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6InlyZVgyUHNMaS1xa2JSOFFET21CX3lTeHA4USJ9.eyJ2ZXIiOiIyLjAiLCJpc3MiOiJodHRwczovL2xvZ2luLm1pY3Jvc29mdG9ubGluZS5jb20vOTE4ODA0MGQtNmM2Ny00YzViLWIxMTItMzZhMzA0YjY2ZGFkL3YyLjAiLCJzdWIiOiJBQUFBQUFBQUFBQUFBQUFBQUFBQUFDcFpQMFZ2dHlYTnc5X1JPaklVNmZZIiwiYXVkIjoiM2M3Y2I2ZGEtZGFiMC00ODY5LWI0NzktZWIxZjhlODYwMzEyIiwiZXhwIjoxNjkwNTM4MjM3LCJpYXQiOjE2OTA0NTE1MzcsIm5iZiI6MTY5MDQ1MTUzNywibmFtZSI6IkxlaGxvaG9ub2xvIFNpcmUiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJsLndpemFyZC5oQGdtYWlsLmNvbSIsIm9pZCI6IjAwMDAwMDAwLTAwMDAtMDAwMC03NGQzLWExNzVlYTNlMjk1MSIsInRpZCI6IjkxODgwNDBkLTZjNjctNGM1Yi1iMTEyLTM2YTMwNGI2NmRhZCIsIm5vbmNlIjoiOTUxMDZkMDgtMjgyYi00NzE4LTk3NTktNGM1YmMwMjQ5MGI2IiwiYWlvIjoiRFJTejM3WE9nblM4NlFIWXZkQk5VdDJITGVsVCpFNElSRnBSZ0hialhrcmw4bDRmbTVCcktwNDZFU0dpIW1GNW9XN0ZFTTZ6TFBOR3Z4S0FZNnhGcFpNIW5BcTZmMWFCb21rTSFWSHNFaXNZNUI1cUhadEo2VUxCRFZuN3hYd2g1KlAxZzQ1TWhSMnlGNEo2YnY4SWl4byQifQ.cTCJCeTKKL4tpxWqtxdlmOyhRYNFgNCmOUVuCnb1F13efogHiOJscua-kmPb0AUTjMcf278illq36noesadGX30pe7SQNrSHsuwLfxiTQPZtXhzkdgQ72eViVRORBHZoQA62c4zUK0I72uo7M5wHbwfV_kSfV5EwMl5suRSnN9WUr5I43Go_SeFkKHqQ9lxTufpImqBaPHuf_07NtqoIkIySjOl5iUQs8o8EIeScYN_MV80WkAqhKmPE3ffCl8oUFavgW8Kw9PxPVeqozwFfhH_5GWI9gT0IzVpTsurcxnBIzY57RBdgmaC14UlReVG5XL3RSs9HX6ClrQuLKOyFzQ'
    }
  });
}

async function apiPost(endpoint, body) {
  return await fetch(allaboard_url + endpoint, {
    method: "POST",
    body: body,
    headers: {
      'Content-type': 'application/json; charset=UTF-8',
      'Authorization': 'Bearer ' + localStorage.getItem('token'),
    }
  });
}

const slidePage = document.querySelector(".slide-page");
const nextBtnFirst = document.querySelector(".firstNext");
const prevBtnSec = document.querySelector(".prev-1");
const nextBtnSec = document.querySelector(".next-1");
const prevBtnThird = document.querySelector(".prev-2");
const nextBtnThird = document.querySelector(".next-2");
const prevBtnFourth = document.querySelector(".prev-3");
const submitBtn = document.querySelector(".submit");
const progressText = document.querySelectorAll(".step p");
const progressCheck = document.querySelectorAll(".step .check");
const bullet = document.querySelectorAll(".step .bullet");
const errorLbl = document.getElementById('book-error-lbl')
const fullname = document.getElementById('name');
const age = document.getElementById('age');
const seatType = document.getElementById('seat-type-dropdown');
const trainClass = document.getElementById('train-class-dropdown');

let current = 1;

nextBtnFirst.addEventListener("click", function (event) {
  event.preventDefault();
  slidePage.style.marginLeft = "-25%";
  bullet[current - 1].classList.add("active");
  progressCheck[current - 1].classList.add("active");
  progressText[current - 1].classList.add("active");
  current += 1;
});
nextBtnSec.addEventListener("click", async function (event) {

  event.preventDefault();
  slidePage.style.marginLeft = "-50%";
  bullet[current - 1].classList.add("active");
  progressCheck[current - 1].classList.add("active");
  progressText[current - 1].classList.add("active");
  current += 1;
});
nextBtnThird.addEventListener("click", function (event) {
  event.preventDefault();

  if (age.value === "" || fullname.value === "") {
    errorLbl.style.display = "block";
    errorLbl.textContent = "please fill in your fullname and age";
    localStorage.setItem('fullname', fullname.value);
    console.log(localStorage.getItem(fullname))
  }
  else if (seatType.value === "disabled selected" || trainClass.value === "disabled selected") {
    errorLbl.style.display = "block";
    errorLbl.textContent = "please select seat type and train class"
  } else {
    let data = JSON.stringify({
      "user_email": localStorage.getItem('mail'),
      "train_class": trainClass.value,
      "passengers": [{
        "age": age.value,
        "passenger_name": fullname.value,
        "seat_type": seatType.value,
      }]
    });

    localStorage.setItem('fullname', fullname.value);
    localStorage.setItem('age', age.value);
    localStorage.setItem('seatType', seatType.value);
    localStorage.setItem('trainClass', trainClass.value);

    console.log(localStorage.getItem(fullname))

     apiPost('/booking/booking', data)

    slidePage.style.marginLeft = "-75%";
    bullet[current - 1].classList.add("active");
    progressCheck[current - 1].classList.add("active");
    progressText[current - 1].classList.add("active");
    current += 1;
  }


});
submitBtn.addEventListener("click", function () {
  bullet[current - 1].classList.add("active");
  progressCheck[current - 1].classList.add("active");
  progressText[current - 1].classList.add("active");
  current += 1;
  setTimeout(function () {
    alert("You have booked successfully");
    localStorage.clear();
    location.reload();
  }, 800);
});

prevBtnSec.addEventListener("click", function (event) {
  event.preventDefault();
  slidePage.style.marginLeft = "0%";
  bullet[current - 2].classList.remove("active");
  progressCheck[current - 2].classList.remove("active");
  progressText[current - 2].classList.remove("active");
  current -= 1;
});
prevBtnThird.addEventListener("click", function (event) {
  event.preventDefault();
  slidePage.style.marginLeft = "-25%";
  bullet[current - 2].classList.remove("active");
  progressCheck[current - 2].classList.remove("active");
  progressText[current - 2].classList.remove("active");
  current -= 1;
});
prevBtnFourth.addEventListener("click", function (event) {
  event.preventDefault();
  slidePage.style.marginLeft = "-50%";
  bullet[current - 2].classList.remove("active");
  progressCheck[current - 2].classList.remove("active");
  progressText[current - 2].classList.remove("active");
  current -= 1;
});

