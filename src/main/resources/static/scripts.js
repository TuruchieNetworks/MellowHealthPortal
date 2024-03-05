/**
 * 
 */
// custom.js
function updateSelectedPatient(patientSelectId, inputReadOnlyId, selectedPatientDivId) {
    var patientSelect = document.getElementById(patientSelectId);
    var inputReadOnly = document.getElementById(inputReadOnlyId);
    var selectedPatientDiv = document.getElementById(selectedPatientDivId);

    // Check if a patient has been selected
    if (patientSelect.value !== '') {
	        var selectedPatientName = patientSelect.options[patientSelect.selectedIndex].text;
	
	        // Update the input field
	        inputReadOnly.value = selectedPatientName;
	
	        // Update the content of the div outside the form
	        selectedPatientDiv.innerText = 'Selected Patient: ' + selectedPatientName;
	
	        // Show the details div (if needed)
	        // detailsDiv.style.display = 'block';
	    }
	}
	
	document.addEventListener('DOMContentLoaded', function () {
	    // Trigger the updateSelectedPatient function manually on document ready
	    updateSelectedPatient('patientSelect', 'patientName', 'selectedPatientDiv');
	});

$(document).ready(() => {
    // Bind click event to the search button
    $("#searchPatientButton").click(() => {
        // Fetch the searched patients using AJAX
        $.ajax({
            url: "/mellowHealth/hospitalDashboard/searchPatients",  // Modify the URL accordingly
            type: "GET",
            data: { searchedPatientName: $("#searchedPatientName").val() },
            success: (data) => {
                // Clear previous search results
                $("#searchedPatientsContainer").empty();

                // Check if there are any searched patients
                if (data.length > 0) {
                    // Display the searched patients in the container
                    const container = $("#searchedPatientsContainer");

                    // Iterate through each patient and create a display element
                    data.forEach((patientCase) => {
                        const patientLink = $("<a>")
                            .attr("href", `/mellowHealth/hospitalDashboard/patientCases/${patientCase.patient.id}`)
                            .text(`${patientCase.patient.patientFirstName} ${patientCase.patient.patientLastName} Date Of Birth: ${patientCase.patient.dateOfBirth}`);

                        const paragraph = $("<p>")
                            .addClass("btn btn-outline-primary form-control")
                            .css({
                                "color": "rgba(311, 31, 321, 0.9)",
                                "background": "rgba(11, 0.31, 1, 0.9)"
                            })
                            .append(patientLink);

                        container.append(paragraph);
                    });

                    // Show the container
                    container.show();
                } else {
                    // No search results, hide the container
                    $("#searchedPatientsContainer").hide();
                }
            },
            error: () => {
                console.error("Error fetching searched patients");
            }
        });
    });
});

document.addEventListener('DOMContentLoaded', function () {
    // Trigger the updateSelectedPatient function manually on document ready
    updateSelectedPatient('patientSelect', 'patientName', 'selectedPatientDiv');
    
    // Display search instructions on page load
    showSearchInstructions();
});

function showSearchInstructions() {
    // You can customize this message based on your needs
    var instructions = "To view insurance information, please search for a patient.";

    // Create a div element for the instructions
    var instructionsDiv = document.createElement('div');
    instructionsDiv.innerHTML = instructions;
    instructionsDiv.style.color = 'blue'; // Customize the style as needed

    // Append the instructions div to the container where you want to display it
    var container = document.getElementById('searchInstructionsContainer');
    container.appendChild(instructionsDiv);
}

function displayInstructions(containerId, message, textColor = 'blue') {
    // Create a div element for the instructions
    var instructionsDiv = document.createElement('div');
    instructionsDiv.innerHTML = message;
    instructionsDiv.style.color = textColor;

    // Get the container where you want to display the instructions
    var container = document.getElementById(containerId);

    // Clear previous content
    container.innerHTML = '';

    // Append the instructions div to the container
    container.appendChild(instructionsDiv);
}
// custom.js or your script file

// Function to display instructions dynamically
function displayInstructions(targetDivId, message) {
    var targetDiv = document.getElementById(targetDivId);
    if (targetDiv) {
        targetDiv.innerText = message;
        // You can add additional styling or animation if needed
        // targetDiv.style.color = 'your-color';
        // targetDiv.style.fontSize = 'your-font-size';
        // ...
    }
}

// Other functions or script code

function hideSearchInstructions() {
    // Hide the instructions div
    var container = document.getElementById('searchInstructionsContainer');
    container.style.display = 'none';
}

// Function to add country code to phone numbers
function addCountryCodesToPhoneNumber() {
    // Get the selected country code and phone number
    var countryCode = $("#countryCode").val();
    var phoneNumber = $("#phoneNumber").val();

    // Check if both country code and phone number are present
    if (countryCode && phoneNumber) {
        // Concatenate country code and phone number
        var formattedPhoneNumber = countryCode + phoneNumber;

        // Update the phone number input field with the formatted value
        $("#phoneNumber").val(formattedPhoneNumber);
    }
}

// Function to add country code to phone numbers
function addCountryCodeToPhoneNumber() {
    // Get the selected country code and phone number
    var selectedValue = $("#countryCode").val();
    var countryCode = selectedValue.split(' ')[0];
    var phoneNumber = $("#phoneNumber").val();

    // Do something with countryCode and phoneNumber
    console.log("Country Code: " + countryCode);
    console.log("Phone Number: " + phoneNumber);
}

function addCountryCodeToPhoneNumberBySubmit() {
    // Get the selected country code and phone number
    var countryCode = $("#countryCode").val();
    var phoneNumber = $("#phoneNumber").val();

    // Check if both country code and phone number are present
    if (countryCode && phoneNumber) {
        // Concatenate country code and phone number
        var formattedPhoneNumber = countryCode + phoneNumber;

        // Update the phone number input field with the formatted value
        $("#phoneNumber").val(formattedPhoneNumber);
    }
}

// Function to dynamically add country code to phone numbers
/*Now, both functions (addCountryCodeToPhoneNumber and addCountryCodeToPhoneNumberBySubmit) call the common function updatePhoneNumberValue.
This way, you have a single place to manage the logic, making it more maintainable.
If you need to update the behavior in the future, you only need to do it in one location.*/
function dynamicallyAddCountryCodeToPhoneNumber() {
    updatePhoneNumberValue();
}

// Function to be called when the form is submitted
function dynamicallyAddCountryCodeToPhoneNumberBySubmit() {
    updatePhoneNumberValue();
}

// Common function to update the phone number value
function updatePhoneNumberValue() {
    // Get the selected country code and phone number
    var countryCode = $("#countryCode").val();
    var phoneNumber = $("#phoneNumber").val();

    // Check if both country code and phone number are present
    if (countryCode && phoneNumber) {
        // Concatenate country code and phone number
        var formattedPhoneNumber = countryCode + phoneNumber;

        // Update the phone number input field with the formatted value
        $("#phoneNumber").val(formattedPhoneNumber);
    }
}

// Wait for the document to be ready
    document.addEventListener('DOMContentLoaded', function () {

        // Show or hide the button based on scroll position
        window.addEventListener('scroll', function () {
            var scrollToTopBtn = document.getElementById('scrollToTopBtn');
            if (window.scrollY > 100) {
                scrollToTopBtn.style.display = 'block';
            } else {
                scrollToTopBtn.style.display = 'none';
            }
        });

        // Scroll to top when the button is clicked
        document.getElementById('scrollToTopBtn').addEventListener('click', function () {
            scrollToTop();
        });

        // Function to scroll smoothly to the top
        function scrollToTop() {
            var scrollStep = -window.scrollY / (500 / 15); // Adjust the speed by changing the division values
            var scrollInterval = setInterval(function () {
                if (window.scrollY !== 0) {
                    window.scrollBy(0, scrollStep);
                } else {
                    clearInterval(scrollInterval);
                }
            }, 15);
        }

    });
// Function to scroll smoothly to the top
function scrollToTop() {
    var scrollStep = -window.scrollY / (250 / 15); // Adjust the speed by changing the division values
    var scrollInterval = setInterval(function () {
        if (window.scrollY !== 0) {
            window.scrollBy(0, scrollStep);
        } else {
            clearInterval(scrollInterval);
        }
    }, 15);
}


// Wait for the document to be ready
document.addEventListener('DOMContentLoaded', function () {

    // Show or hide the button based on scroll position
    window.addEventListener('scroll', function () {
        var scrollToTopBtn = document.getElementById('scrollToTopBtn');
        if (window.scrollY > 100) {
            scrollToTopBtn.style.display = 'block';
        } else {
            scrollToTopBtn.style.display = 'none';
        }
    });

    // Scroll to top when the button is clicked
    document.getElementById('scrollToTopBtn').addEventListener('click', function () {
        scrollToTop();
    });

    // Function to scroll smoothly to the top
    function scrollToTop() {
        var scrollStep = -window.scrollY / (100 / 15); // Adjust the speed by changing the division values
        var scrollInterval = setInterval(function () {
            if (window.scrollY !== 0) {
                window.scrollBy(0, scrollStep);
            } else {
                clearInterval(scrollInterval);
            }
        }, 15);
    }

});

// Wait for the document to be ready
document.addEventListener('DOMContentLoaded', function () {

    // Show or hide the button based on scroll position
    window.addEventListener('scroll', function () {
        var scrollToTopBtn = document.getElementById('scrollToTopBtn');
        if (window.scrollY > 100) {
            scrollToTopBtn.style.display = 'block';
        } else {
            scrollToTopBtn.style.display = 'none';
        }
    });

    // Scroll to top when the button is clicked
    document.getElementById('scrollToTopBtn').addEventListener('click', function () {
        scrollToTop();
    });

    // Function to scroll smoothly to the top
    function scrollToTop() {
        var scrollStep = -window.scrollY / (100 / 15); // Adjust the speed by changing the division values
        var scrollInterval = setInterval(function () {
            if (window.scrollY !== 0) {
                window.scrollBy(0, scrollStep);
            } else {
                clearInterval(scrollInterval);
            }
        }, 15);
    }

});
