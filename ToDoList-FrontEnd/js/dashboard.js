document.addEventListener("DOMContentLoaded", () => {
  const newCategoryBtn = document.getElementById("new-category");
  const categoryContainer = document.getElementById("category-container");

  const modalCategory = document.getElementById("modal-category");
  const categoryNameInput = document.getElementById("category-name");
  const cancelCategory = document.getElementById("cancel-category");
  const confirmCategory = document.getElementById("confirm-category");

  const modalTask = document.getElementById("modal-task");
  const taskNameInput = document.getElementById("task-name");
  const taskDescInput = document.getElementById("task-desc");
  const taskDeadlineInput = document.getElementById("task-deadline");
  const taskProgressInput = document.getElementById("task-progress");
  const cancelTask = document.getElementById("cancel-task");
  const confirmTask = document.getElementById("confirm-task");

  const modalConfirm = document.getElementById("modal-confirm");
  const yesDelete = document.getElementById("yes-delete");
  const noDelete = document.getElementById("no-delete");

  const modalLogout = document.getElementById("modal-logout");
  const logoutBtn = document.getElementById("logout-btn");
  const yesLogout = document.getElementById("yes-logout");
  const noLogout = document.getElementById("no-logout");

  const goAccount = document.getElementById("go-account");

  const dropdownBtn = document.getElementById("dropdownBtn");
  const dropdownMenu = document.getElementById("dropdownMenu");

  let currentCategory = null;
  let editingTask = null;
  let deleteTarget = null;

  dropdownBtn.addEventListener("click", () => {
    dropdownMenu.style.display = dropdownMenu.style.display === "flex" ? "none" : "flex";
  });

  if (goAccount) {
    goAccount.addEventListener("click", () => {
      window.location.href = "account.html";
    });
  }

  if (logoutBtn) {
    logoutBtn.addEventListener("click", () => {
      modalLogout.style.display = "flex";
    });
  }

  yesLogout.addEventListener("click", () => {
    window.location.href = "login.html";
  });

  noLogout.addEventListener("click", () => {
    modalLogout.style.display = "none";
  });

  newCategoryBtn.addEventListener("click", () => {
    modalCategory.style.display = "flex";
  });

  cancelCategory.addEventListener("click", () => {
    modalCategory.style.display = "none";
    categoryNameInput.value = "";
  });

  confirmCategory.addEventListener("click", () => {
    const name = categoryNameInput.value.trim();
    if (name === "") return;

    const categoryEl = document.createElement("div");
    categoryEl.className = "category";

    const header = document.createElement("div");
    header.className = "category-header";
    header.innerHTML = `<h3>${name}</h3>
      <div>
        <button class="add-task">Add Task ++</button>
        <button class="delete-category">Delete</button>
      </div>`;

    const taskList = document.createElement("div");
    taskList.className = "task-list";

    categoryEl.appendChild(header);
    categoryEl.appendChild(taskList);
    categoryContainer.appendChild(categoryEl);

    modalCategory.style.display = "none";
    categoryNameInput.value = "";

    header.querySelector(".add-task").addEventListener("click", () => {
      currentCategory = taskList;
      editingTask = null;
      modalTask.style.display = "flex";
    });

    header.querySelector(".delete-category").addEventListener("click", () => {
      deleteTarget = categoryEl;
      modalConfirm.style.display = "flex";
    });
  });

  cancelTask.addEventListener("click", () => {
    modalTask.style.display = "none";
    clearTaskInputs();
  });

  confirmTask.addEventListener("click", () => {
    const title = taskNameInput.value.trim();
    const desc = taskDescInput.value.trim();
    const deadline = taskDeadlineInput.value;
    const progress = taskProgressInput.value;

    if (!title || !deadline || !progress) return;

    const taskEl = editingTask || document.createElement("div");
    taskEl.className = "task";

    taskEl.innerHTML = `
      <div class="task-info">
        <div class="task-title">${title}</div>
        <div class="task-desc">${desc}</div>
        <div class="task-deadline">
          <span style="color: red">Deadline: ${deadline}</span> |
          <span style="color: green">Your Progress: ${progress}%</span>
        </div>
      </div>
      <div class="task-actions">
        <button class="edit-task">✏️</button>
        <button class="delete-task">🗑️</button>
      </div>`;

    if (!editingTask) currentCategory.appendChild(taskEl);

    taskEl.querySelector(".edit-task").addEventListener("click", () => {
      editingTask = taskEl;
      currentCategory = taskEl.parentElement;
      taskNameInput.value = title;
      taskDescInput.value = desc;
      taskDeadlineInput.value = deadline;
      taskProgressInput.value = progress;
      modalTask.style.display = "flex";
    });

    taskEl.querySelector(".delete-task").addEventListener("click", () => {
      deleteTarget = taskEl;
      modalConfirm.style.display = "flex";
    });

    modalTask.style.display = "none";
    clearTaskInputs();
  });

  yesDelete.addEventListener("click", () => {
    if (deleteTarget) deleteTarget.remove();
    modalConfirm.style.display = "none";
    deleteTarget = null;
  });

  noDelete.addEventListener("click", () => {
    modalConfirm.style.display = "none";
    deleteTarget = null;
  });

  function clearTaskInputs() {
    taskNameInput.value = "";
    taskDescInput.value = "";
    taskDeadlineInput.value = "";
    taskProgressInput.value = "";
    editingTask = null;
  }
});
