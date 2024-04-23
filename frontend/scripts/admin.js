let mainTag = (document.getElementsByTagName('main'));

async function fetchStations() {
  
  let results = await (await apiGet('/station/stations')).json();
  
  let stations = results.data;
  console.log(stations);
  let tab = document.createElement('nav');
  
  tab.className = 'tab';
  for (let i = 0; i < stations.length; i++ ) {
    let stationName = stations[i].station_name;
    let navButton = document.createElement('button');
    navButton.id = stationName;
    navButton.textContent = stationName;
    navButton.addEventListener('click', function(e) {
      openStation(stationName);
    });
    // navButton.onclick = openStation(stationName);
    navButton.className = 'tablinks';
    tab.appendChild(navButton);
  }
  console.log(mainTag);
  mainTag[0].appendChild(tab);
}

fetchStations();

async function openStation(stationName) {
  let results = await (await apiGet('/train/getTrainsBasedOnStation?from='+stationName)).json();
  let trains = results.data;
  console.log(trains);

  tabcontent = document.getElementsByClassName("tabcontent");
  for (i = 0; i < tabcontent.length; i++) {
    tabcontent[i].style.display = "none";
  }
  let stationSection = document.createElement('section');
  stationSection.className = 'tabcontent';

  let container = document.createElement('section');
  container.className = 'train-container';

  let addButton = document.createElement('button');
  addButton.id = 'add-train';
  addButton.textContent = 'Add train'; 
  stationSection.appendChild(addButton);

  let selectElement = document.createElement('select');
  selectElement.className = 'train-list';
  selectElement.multiple = 'multiple';

  for (let i = 0; i < trains.length; i++) {
    let optionElement = document.createElement('option');
    optionElement.textContent = trains[i].train_name;
    optionElement.addEventListener('click', function(e) {
      console.log(container);
      populateTrainInfo(container, trains[i]);
    })
    selectElement.appendChild(optionElement);
  }
  container.appendChild(selectElement);
  stationSection.appendChild(container);
  mainTag[0].appendChild(stationSection);
    // Declare all variables
    // var i, tabcontent, tablinks;
  
    // // Get all elements with class="tabcontent" and hide them
    // tabcontent = document.getElementsByClassName("tabcontent");
    // for (i = 0; i < tabcontent.length; i++) {
    //   tabcontent[i].style.display = "none";
    // }
  
    // // Get all elements with class="tablinks" and remove the class "active"
    // tablinks = document.getElementsByClassName("tablinks");
    // for (i = 0; i < tablinks.length; i++) {
    //   tablinks[i].className = tablinks[i].className.replace(" active", "");
    // }
  
    // // Show the current tab, and add an "active" class to the button that opened the tab
    // document.getElementById(cityName).style.display = "flex";
    // evt.currentTarget.className += " active";
}

formElements = {
  'Train Name': 'label',
  'train_name': 'input',
  'Destination station': 'label',
  'destination_station.station_name': 'input',
  'Departure time': 'label',
  'departure_time': 'input'
}

buttons = {
  'edit-button': ['Edit', edit],
  'submit-button': ['Okay', exitEdit],
  'cancel-button': ['Cancel', exitEdit],
}

function populateTrainInfo(tabContent, trainObject) {
  tabcontent = document.getElementsByClassName("train-info");
  for (i = 0; i < tabcontent.length; i++) {
    tabcontent[i].style.display = "none";
  }
  let trainInfo = document.createElement('article');
  trainInfo.className = 'train-info';

  for (const key in formElements) {
    let value = formElements[key];
    let element = document.createElement(value);
    if (value === 'label') 
      element.textContent = key;
    else{
      element.id = key;
      element.value = trainObject[key];
    }
      


    console.log(element);
    trainInfo.appendChild(element);
  }

  for (const key in buttons) {
    let element = document.createElement('button');
    element.id = key;
    element.textContent = buttons[key][0];
    element.addEventListener('click', function(e) {
      let action = buttons[key][1];
      action();
    });
    trainInfo.appendChild(element);
  }
  tabContent.appendChild(trainInfo);
  
  console.log(trainObject);
}

function edit() {
  console.log('Editing');
  document.getElementById('submit-button').style.visibility = 'visible';
  document.getElementById('cancel-button').style.visibility = 'visible';
  document.getElementById('edit-button').style.visibility = 'hidden';
}

function exitEdit() {
    document.getElementById('submit-button').style.visibility = 'hidden';
    document.getElementById('cancel-button').style.visibility = 'hidden';
    document.getElementById('edit-button').style.visibility = 'visible';
}