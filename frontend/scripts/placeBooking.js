
document.addEventListener('DOMContentLoaded', () => {

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


    const fullname = document.getElementById('name');
    const age = document.getElementById('age');
    const seatType = document.getElementById('seat-type-dropdown');
    const trainClass = document.getElementById('train-class-dropdown');
    const nextBtn = document.getElementById('book-btn');
    const errorLbl = document.getElementById('error-lbl')


    async function populateDropdowns() {
        const seatTypeD = await (await apiGet('/seatType/seatTypes')).json();
        const seatTypeData = seatTypeD.data;
        seatTypeData.forEach(seat => {
            const option = document.createElement('option');
            option.value = seat.seat_type_id;
            option.textContent = seat.seat_type_name;
            seatType.appendChild(option);
        });

        const trainClassD = await (await apiGet('/trainClass/trainClasses')).json();
        const trainClassData = trainClassD.data;

        trainClassData.forEach(classType => {
            const option = document.createElement('option');
            option.value = classType.class_type_id;
            option.textContent = classType.class_type_name;
            trainClass.appendChild(option);
        })
    }
    populateDropdowns();


    async function makeBooking() {
        let data = JSON.stringify({
            "user_email": localStorage.getItem('mail'),
            "train_class": trainClass.value,
            "passengers": [{
                "age": age.value,
                "passenger_name": fullname.value,
                "seat_type":seatType.value,
            }]
        });

        localStorage.setItem('fullname', fullname.value);
        localStorage.setItem('age', age.value);
        localStorage.setItem('seatType', seatType.value);
        localStorage.setItem('trainClass', trainClass.value);

        const newpassenger = apiPost('/booking/booking',data)
    }

});




