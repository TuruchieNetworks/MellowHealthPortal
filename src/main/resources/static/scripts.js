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

