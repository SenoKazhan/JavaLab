
    let isCountriesListVisible = false;

    function toggleCountriesList() {
    if (isCountriesListVisible) {
    document.getElementById('allCountries').innerHTML = '';
    isCountriesListVisible = false;
} else {
    findAllCountries();
    isCountriesListVisible = true;
}
}

    function findAllCountries() {
    fetch('http://localhost:8080/api/countries')
        .then(response => response.json())
        .then(countries => {
            const allCountriesDiv = document.getElementById('allCountries');
            allCountriesDiv.innerHTML = "<h3>All Countries:</h3>";

            const table = document.createElement('table');
            table.innerHTML = `
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Code</th>
                <th>Phone</th>
                <th>Cities</th>
                <th>Actions</th>
            </tr>
        `;

            countries.forEach(country => {
                const cities = country.cities.map(city => city.name).join(', ');
                const row = document.createElement('tr');
                row.innerHTML = `
                <td>${country.id}</td>
                <td>${country.name}</td>
                <td>${country.code}</td>
                <td>${country.phone}</td>
                <td>${cities}</td>
                <td>
                    <button onclick="updateCountry(${country.id})">Update</button>
                    <button onclick="deleteCountry(${country.id})">Delete</button>
                </td>
            `;
                table.appendChild(row);
            });

            allCountriesDiv.appendChild(table);
        });
}

    function createCountry() {
    const name = document.getElementById('name').value;
    const code = document.getElementById('code').value;
    const phone = document.getElementById('phone').value;

    const country = {
    name: name,
    code: code,
    phone: phone
};

    fetch('http://localhost:8080/api/countries', {
    method: 'POST',
    headers: {
    'Content-Type': 'application/json'
},
    body: JSON.stringify(country)
})
    .then(response => response.json())
    .then(newCountry => {
    alert('Country created successfully!');
    findAllCountries();
})
    .catch(error => {
    console.error('Error:', error);
});
}

    function updateCountry(countryId) {
    const newName = prompt('Enter the new name for the country:');
    const newCode = prompt('Enter the new country code for the country:');
    const newPhone = prompt('Enter the new phone code for the country:');

    const updatedCountry = {};

    if (newName !== null && newName.trim() !== '') {
    updatedCountry.name = newName.trim();
}

    if (newPhone !== null && newPhone.trim() !== '') {
    updatedCountry.phone = newPhone.trim();
}

    if (newCode !== null && newCode.trim() !== '') {
    updatedCountry.code = newCode.trim();
}

    if (Object.keys(updatedCountry).length === 0) {
    alert('No changes were made.');
    return;
}

    fetch(`http://localhost:8080/api/countries/${countryId}`, {
    method: 'PUT',
    headers: {
    'Content-Type': 'application/json'
},
    body: JSON.stringify(updatedCountry)
})
    .then(response => {
    if (response.ok) {
    alert('Country updated successfully!');
    findAllCountries();
} else {
    console.error('Error:', response.status);
}
})
    .catch(error => {
    console.error('Error:', error);
});
}

    function deleteCountry(countryId) {
    if (confirm('Are you sure you want to delete this country?')) {
    fetch(`http://localhost:8080/api/countries/${countryId}`, {
    method: 'DELETE'
})
    .then(response => {
    if (response.ok) {
    alert('Country deleted successfully!');
    findAllCountries();
} else {
    console.error('Error:', response.status);
}
})
    .catch(error => {
    console.error('Error:', error);
});
}
}

    findAllCountries();