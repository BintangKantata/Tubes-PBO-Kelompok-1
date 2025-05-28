document.addEventListener("DOMContentLoaded", () => {
  // Modal logic
  const modal = document.getElementById("terms-modal");
  const openBtn = document.getElementById("open-terms");
  const closeBtn = document.querySelector(".close");

  openBtn.addEventListener("click", function (e) {
    e.preventDefault();
    modal.style.display = "block";
  });

  closeBtn.addEventListener("click", function () {
    modal.style.display = "none";
  });

  window.addEventListener("click", function (e) {
    if (e.target === modal) {
      modal.style.display = "none";
    }
  });

  // Validasi form
  const username = document.getElementById("username");
  const email = document.getElementById("email");
  const password = document.getElementById("password");
  const agree = document.querySelector("input[name='agree']");
  const submitBtn = document.querySelector(".login-btn");

  submitBtn.addEventListener("click", (e) => {
    e.preventDefault();

    if (
      username.value.trim() === "" ||
      email.value.trim() === "" ||
      password.value.trim() === ""
    ) {
      alert("Oops! It looks like some fields are empty. Please fill them in"); 
      return;
    }

    if (!validateEmail(email.value.trim())) {
      alert("Invalid email format.");
      return;
    }

    if (!agree.checked) {
      alert("You must agree to the terms and conditions.");
      return;
    }

    alert("Registration successful!");
  });

  function validateEmail(email) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
  }
});
