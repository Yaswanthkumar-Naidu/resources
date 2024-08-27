let globalData; // Global variable to store fetched data

document.addEventListener('DOMContentLoaded', function() {

	const savedInterval = localStorage.getItem('refreshInterval') || 5;

	const refreshDropdown = document.getElementById('refreshInterval');

	const moduleDropdown = document.getElementById('moduleDropdown');

	const browserDropdown = document.getElementById('browserDropdown');

	setRefresh(savedInterval);

	refreshDropdown.value = savedInterval;

	// Fetch data and store in global variable

	fetch('data.json')

		.then(response => response.json())

		.then(data => {

			globalData = data;

			createPieChart(data.testCaseStats);

			createGroupedBarChart(data.modules, 'groupedModuleChart');

			createGroupedBarChart(data.browsers, 'groupedBrowserChart');

			populateDropdown(['All', ...data.modules.map(module => module.name)], moduleDropdown);

			populateDropdown(['All', ...data.browsers.map(browser => browser.name)], browserDropdown);

			moduleDropdown.value = localStorage.getItem('moduleFilter') || 'All';

			browserDropdown.value = localStorage.getItem('browserFilter') || 'All';

			populateTestCaseTable(data.testCases, moduleDropdown.value, browserDropdown.value);

			populateModuleExecutionTable(data.modules);

		});

	// Event listeners for dropdown changes

	moduleDropdown.addEventListener('change', function() {

		localStorage.setItem('moduleFilter', this.value);

		populateTestCaseTable(globalData.testCases, this.value, browserDropdown.value);

	});

	browserDropdown.addEventListener('change', function() {

		localStorage.setItem('browserFilter', this.value);

		populateTestCaseTable(globalData.testCases, moduleDropdown.value, this.value);

	});

	refreshDropdown.addEventListener('change', function() {

		setRefresh(this.value);

	});

});

// ... other functions (createPieChart, createGroupedBarChart, setRefresh, populateDropdown, populateTestCaseTable) ...


function createPieChart(testCaseStats) {

	const pieCtx = document.getElementById('testCasePieChart').getContext('2d');

	new Chart(pieCtx, {

		type: 'pie',

		data: {

			labels: ['Passed', 'Failed', 'Pending', 'Skipped'],

			datasets: [{

				label: 'Test Case Statistics',

				data: [testCaseStats.passed, testCaseStats.failed, testCaseStats.pending, testCaseStats.skipped],

				backgroundColor: [

					'rgba(0, 128, 0, 0.6)', // Green for Passed

					'rgba(255, 0, 0, 0.6)', // Red for Failed

					'rgba(173, 216, 230, 0.6)', // Light blue for Pending

					'rgba(255, 255, 0, 0.6)' // Yellow for Skipped

				],

				borderColor: [

					'rgba(0, 128, 0, 1)',

					'rgba(255, 0, 0, 1)',

					'rgba(173, 216, 230, 1)',

					'rgba(255, 255, 0, 1)'

				],

				borderWidth: 1

			}
			]

		},

		options: {

			plugins: {

				datalabels: {

					color: '#000',

					formatter: (value, ctx) => {

						return value;

					}

				}

			},

			layout: {

				padding: {

					left: 50

				}

			},

			legend: {

				position: 'left'

			},

			maintainAspectRatio: false

		}

	});

}

function createGroupedBarChart(data, canvasId) {

	const ctx = document.getElementById(canvasId).getContext('2d');

	const labels = data.map(item => item.name);

	const passedData = data.map(item => item.passed);

	const failedData = data.map(item => item.failed);

	const pendingData = data.map(item => item.pending);

	const skippedData = data.map(item => item.skipped);

	new Chart(ctx, {

		type: 'bar',

		data: {

			labels: labels,

			datasets: [
				{

					label: 'Passed',

					data: passedData,

					backgroundColor: 'rgba(0, 128, 0, 0.6)',

					borderColor: 'rgba(0, 128, 0, 1)',

					borderWidth: 1

				},
				{

					label: 'Failed',

					data: failedData,

					backgroundColor: 'rgba(255, 0, 0, 0.6)',

					borderColor: 'rgba(255, 0, 0, 1)',

					borderWidth: 1

				},
				{

					label: 'Pending',

					data: pendingData,

					backgroundColor: 'rgba(173, 216, 230, 0.6)', // Lighter blue (Sky Blue)

					borderColor: 'rgba(173, 216, 230, 1)',

					borderWidth: 1

				},
				{

					label: 'Skipped',

					data: skippedData,

					backgroundColor: 'rgba(255, 255, 0, 0.6)',

					borderColor: 'rgba(255, 255, 0, 1)',

					borderWidth: 1

				}

			]

		},

		options: {

			plugins: {

				datalabels: {

					color: '#000',

					formatter: (value, ctx) => {

						return value;

					}

				}

			},

			scales: {

				x: {
					stacked: true
				},

				y: {
					stacked: true
				}

			}

		}

	});

}

function setRefresh(interval) {

	clearInterval(window.refreshIntervalId);

	window.refreshIntervalId = setInterval(() => location.reload(), interval * 1000);

	localStorage.setItem('refreshInterval', interval);

}

function populateDropdown(items, dropdown) {

	// Clear existing options

	dropdown.innerHTML = '';

	// Add 'All' option

	let allOption = document.createElement('option');

	allOption.value = allOption.textContent = 'All';

	dropdown.appendChild(allOption);

	// Add other options

	items.forEach(item => {

		if (item !== 'All') { // Prevent adding 'All' again

			let option = document.createElement('option');

			option.value = option.textContent = item;

			dropdown.appendChild(option);

		}

	});

}

function populateTestCaseTable(data, moduleFilter = 'All', browserFilter = 'All') {

	const tableBody = document.getElementById('testCaseTable').getElementsByTagName('tbody')[0];

	tableBody.innerHTML = '';

	data.forEach(testCase => {

		if ((moduleFilter === 'All' || testCase.module === moduleFilter) &&

			(browserFilter === 'All' || testCase.browser === browserFilter)) {

			const row = tableBody.insertRow();

			const testCaseLink = `${testCase.testCaseName}_${testCase.module}_${testCase.browser}/TestCase.html`;

			row.innerHTML = `

                <td><a href="${testCaseLink}">${testCase.testCaseName}</a></td>

                <td>${testCase.module}</td>

                <td>${testCase.browser}</td>

                <td>${testCase.status}</td>

            `;

		}

	});

}

function populateModuleExecutionTable(modulesData) {

	const tableBody = document.getElementById('moduleExecutionTable').getElementsByTagName('tbody')[0];

	tableBody.innerHTML = '';

	modulesData.forEach(module => {

		const total = module.passed + module.failed + module.pending + module.skipped + module.inprogress;

		const row = tableBody.insertRow();

		row.innerHTML = `

            <td>${module.name}</td>

			<td>${total}</td>

            <td>${module.passed}</td>

            <td>${module.failed}</td>

            <td>${module.pending}</td>

            <td>${module.skipped}</td>
            `;

	});

}