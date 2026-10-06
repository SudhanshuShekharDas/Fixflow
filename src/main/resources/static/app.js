const API = "/api";

async function showError(res) {
    const err = await res.json().catch(() => ({}));
    alert(err.message || "Something went wrong.");
}

async function loadStats() {
    const s = await fetch(`${API}/dashboard/stats`).then(r => r.json());
    const items = [
        ["Total", s.total], ["Open", s.open], ["Assigned", s.assigned],
        ["In Progress", s.inProgress], ["Resolved", s.resolved]
    ];
    document.getElementById("stats").innerHTML =
        items.map(x => `<div class="card">${x[0]}<strong>${x[1]}</strong></div>`).join("");
}

async function loadRequests() {
    const data = await fetch(`${API}/requests`).then(r => r.json());
    const body = document.getElementById("requests");
    body.innerHTML = data.map(r => `
        <tr>
            <td>${r.id}</td>
            <td>${r.title}</td>
            <td>${r.category}</td>
            <td>${r.priority}</td>
            <td>${r.status}</td>
            <td>${r.deadline ? new Date(r.deadline).toLocaleString() : "-"}</td>
            <td>
                <button class="small-btn" onclick="changeStatus(${r.id}, 'IN_PROGRESS')">Start</button>
                <button class="small-btn" onclick="changeStatus(${r.id}, 'RESOLVED')">Resolve</button>
            </td>
        </tr>`).join("");
}

async function changeStatus(id, status) {
    const res = await fetch(`${API}/requests/${id}/status`, {
        method: "PUT",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({status})
    });
    if (!res.ok) await showError(res);
    await refresh();
}

document.getElementById("requestForm").addEventListener("submit", async e => {
    e.preventDefault();

    const request = {
        title: document.getElementById("title").value,
        category: document.getElementById("category").value,
        location: document.getElementById("location").value,
        priority: document.getElementById("priority").value,
        description: document.getElementById("description").value,
        user: {id: 1}
    };

    const res = await fetch(`${API}/requests`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(request)
    });
    if (!res.ok) await showError(res);

    e.target.reset();
    await refresh();
});

async function refresh() {
    await Promise.all([loadStats(), loadRequests()]);
}

refresh();
