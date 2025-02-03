// JavaScript for form validation and submission

document.addEventListener("DOMContentLoaded", function() {
    const leaveForm = document.getElementById("leaveForm");
    const submitButton = document.getElementById("submitButton");
    
    // Disable submit button by default
    submitButton.disabled = true;

    leaveForm.addEventListener("input", function() {
        const name = document.getElementById("name").value;
        const leaveType = document.getElementById("leaveType").value;
        const startDate = document.getElementById("startDate").value;
        const endDate = document.getElementById("endDate").value;

        if (name && leaveType && startDate && endDate) {
            submitButton.disabled = false; // Enable submit if all fields are filled
        } else {
            submitButton.disabled = true; // Disable submit if any field is empty
        }
    });

    // Form submit event
    leaveForm.addEventListener("submit", function(event) {
        event.preventDefault();

        // Validate the form fields
        if (validateForm()) {
            // Show success message or send data to the server here
            alert("Leave application submitted successfully!");

            // Optionally, you can submit the form to the server
            // leaveForm.submit();
        } else {
            alert("Please fill out all required fields correctly.");
        }
    });

    // Form validation function
    function validateForm() {
        const name = document.getElementById("name").value;
        const leaveType = document.getElementById("leaveType").value;
        const startDate = document.getElementById("startDate").value;
        const endDate = document.getElementById("endDate").value;

        // Check if all required fields are filled
        if (!name || !leaveType || !startDate || !endDate) {
            return false;
        }

        // Additional date validation (start date should be before end date)
        if (new Date(startDate) > new Date(endDate)) {
            alert("Start date cannot be after end date.");
            return false;
        }

        return true;
    }
});
