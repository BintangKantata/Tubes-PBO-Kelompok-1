document.addEventListener("DOMContentLoaded", () => {
  const username = localStorage.getItem("registeredUsername") || "Pacar Jojo";
  const email = localStorage.getItem("registeredEmail") || "HidupBlonde@gmail.com";

  document.getElementById("account-username").value = username;
  document.getElementById("account-email").value = email;

  const dropdownBtn = document.getElementById("dropdownBtn");
  const dropdownMenu = document.getElementById("dropdownMenu");
  const logoutBtn = document.getElementById("logout-btn");
  const modalLogout = document.getElementById("modal-logout");
  const yesLogout = document.getElementById("yes-logout");
  const noLogout = document.getElementById("no-logout");
  const goDashboard = document.getElementById("go-dashboard");

  const saveBtn = document.getElementById("save-account");
  const modalSave = document.getElementById("modal-save");
  const confirmSave = document.getElementById("confirm-save");
  const cancelSave = document.getElementById("cancel-save");

  dropdownBtn.addEventListener("click", () => {
    dropdownMenu.style.display = dropdownMenu.style.display === "flex" ? "none" : "flex";
  });

  if (goDashboard) {
    goDashboard.addEventListener("click", () => {
      window.location.href = "dashboard.html";
    });
  }

  logoutBtn.addEventListener("click", () => {
    modalLogout.style.display = "flex";
  });

  yesLogout.addEventListener("click", () => {
    window.location.href = "login.html";
  });

  noLogout.addEventListener("click", () => {
    modalLogout.style.display = "none";
  });

  saveBtn.addEventListener("click", () => {
    modalSave.style.display = "flex";
  });

  confirmSave.addEventListener("click", () => {
    const newUsername = document.getElementById("new-username").value.trim();
    if (newUsername) {
      localStorage.setItem("registeredUsername", newUsername);
    }
    alert("Changes saved!");
    modalSave.style.display = "none";
    location.reload();
  });

  cancelSave.addEventListener("click", () => {
    modalSave.style.display = "none";
  });
});
