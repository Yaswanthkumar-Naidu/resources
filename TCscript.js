document.addEventListener('DOMContentLoaded', function() {

    const savedInterval = localStorage.getItem('refreshInterval') || 5;
    const refreshDropdown = document.getElementById('refreshInterval');


    setRefresh(savedInterval);
    refreshDropdown.value = savedInterval;

	
	fetch('TCData.json')
    .then(response => response.json())
    .then(data => {
        populateTestCaseDetails(data);
        populateStepDetails(data.testSteps);
        updateCurrentStepDetails(data);

    });

    refreshDropdown.addEventListener('change', function() {
        setRefresh(this.value);
    });
});

function populateTestCaseDetails(testCase) {
    const testCaseDetailsTBody = document.getElementById('testCaseDetails').getElementsByTagName('tbody')[0];
    testCaseDetailsTBody.innerHTML = ''; // Clear existing content in the tbody

    const row = document.createElement('tr');
    row.innerHTML = `
        <td>${testCase.testCaseName}</td>
        <td>${testCase.totalSteps}</td>
        <td>${testCase.passed}</td>
        <td>${testCase.failed}</td>
        <td>${testCase.startTime}</td>
        <td>${testCase.endTime}</td>
        <td>${testCase.status}</td>
    `;
    testCaseDetailsTBody.appendChild(row);
}


function populateStepDetails(testSteps) {
    // Ensure we're targeting the <tbody> element of the table
    const stepDetailsTBody = document.getElementById('stepDetails').getElementsByTagName('tbody')[0];
    stepDetailsTBody.innerHTML = ''; // Clear existing content in the tbody

    testSteps.forEach(step => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${step.stepNo}</td>
            <td>${step.description}</td>
            <td>${step.status}</td>
            <td>${step.startTime}</td>
            <td>${step.endTime}</td>
            <td>${step.duration}</td>
        `;
        stepDetailsTBody.appendChild(row);
    });
}


function populateRefreshDropdown(intervals) {
    const refreshDropdown = document.getElementById('refreshInterval');
    refreshDropdown.innerHTML = ''; // Clear existing options

    intervals.forEach(interval => {
        const option = document.createElement('option');
        option.value = interval;
        option.textContent = `${interval} seconds`;
        refreshDropdown.appendChild(option);
    });
}

function updateCurrentStepDetails(currentStepData) {
    document.getElementById('currentStep').textContent = currentStepData.currentStep;
    document.getElementById('stepStatus').textContent = currentStepData.status;
}

function setRefresh(interval) {
    clearInterval(window.refreshIntervalId);
    window.refreshIntervalId = setInterval(() => location.reload(), interval * 1000);
    localStorage.setItem('refreshInterval', interval);
}
