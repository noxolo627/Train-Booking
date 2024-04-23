
document.addEventListener('DOMContentLoaded', async () => {

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



    const toDropdown = document.getElementById('toDropdown');
    const fromDropdown = document.getElementById('fromDropdown');
    const searchBtn = document.getElementById('search-btn');
    const errorLbl = document.getElementById('error-lbl');
    const travelDate = document.getElementById('travel-date');
    const tbody = document.getElementById('trainTable').getElementsByTagName('tbody')[0];

    async function populateDropdowns() {
        const trainStation = await (await apiGet('/station/stations')).json();
        const data = trainStation.data;

        data.forEach(station => {

            const option = document.createElement('option');
            option.value = station.station_id;
            option.textContent = station.station_name;
            fromDropdown.appendChild(option);
        })

        data.forEach(station => {
            const option = document.createElement('option');
            option.value = station.station_id;
            option.textContent = station.station_name;
            toDropdown.appendChild(option);
        })

    }
    populateDropdowns();


    function searchTrains() {
        const to = toDropdown.value;
        const currentDate = new Date();
        const from = fromDropdown.value;
        const date = new Date(travelDate.value);

        console.log(from)
        console.log(to)
        console.log(date)
        const jsonResponse = [
            {
              "train_name": "Express",
              "time": "10:00 AM",
              "cost": "$25",
              "peak_time": "No"
            },
            {
              "train_name": "Super Express",
              "time": "2:30 PM",
              "cost": "$35",
              "peak_time": "Yes"
            }
            
          ];
          for(let i = 0; i < jsonResponse.length; i++){
            tbody.innerHTML += `<tr>
                                  <td>`+ jsonResponse[i].train_name +`</td>
                                  <td>` + jsonResponse[i].time+ `</td>
                                  <td>` + jsonResponse[i].cost + `</td>
                                  <td>` +jsonResponse[i].peak_time + `</td>
                                </tr>`;
          }
        if (toDropdown.value === "disabled selected" || fromDropdown.value === "disabled selected") {
            errorLbl.style.display = "block";
            errorLbl.textContent = "please select location and destination";
        } else if (toDropdown.value === fromDropdown.value) {
            errorLbl.style.display = "block";
            errorLbl.textContent = "location and destination cannot be the same";
        } else if (date < currentDate) {
            errorLbl.style.display = "block";
            errorLbl.textContent = "Please choose a different date from the future";
        } else {

            errorLbl.style.display = "none";
            

            apiGet('/train/getTrainsBasedOnStation?from=' + from + '&to=' + to + '&date=' + date)
                .then(response => response.json())
                .then(data => {

                    tbody.innerText = "";


                    data.forEach(train => {
                        const row = document.createElement('tr');

                        const trainNameCell = document.createElement('td');
                        trainNameCell.textContent = train.trainName;
                        row.appendChild(trainNameCell);

                        const timeCell = document.createElement('td');
                        timeCell.textContent = train.time;
                        row.appendChild(timeCell);

                        const costCell = document.createElement('td');
                        costCell.textContent = train.cost;
                        row.appendChild(costCell);

                        const peakTimeCell = document.createElement('td');
                        peakTimeCell.textContent = train.peakTime;
                        row.appendChild(peakTimeCell);

                        tbody.appendChild(row);
                    });

                    localStorage.setItem('toDropdownValue', toDropdown.value);
                    localStorage.setItem('fromDropdownValue', fromDropdown.value);
                    localStorage.setItem('travelDateValue', travelDate.value);
                })
                .catch(error => {
                    console.error("Error fetching train data:", error);
                });
        
        }
    }

    searchBtn.addEventListener('click', (event) => {
        event.preventDefault();
        searchTrains();
    });

});
