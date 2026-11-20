const defaults = {
  format: "12",
  timeColor: "cyan",
  dateColor: "green",
  background: "black"
};

const colorValues = {
  cyan: "#00cdff",
  white: "#ffffff",
  yellow: "#ffe14a",
  red: "#ff5b67",
  green: "#3cff5a"
};

const settingsForm = document.querySelector("#settings");
const settingsButton = document.querySelector("#settings-toggle");
const timeElement = document.querySelector("#time");
const dateElement = document.querySelector("#date");
const timezoneElement = document.querySelector("#timezone");
const preferenceKey = "wal-digital-watch-settings";

function loadPreferences() {
  try {
    const saved = JSON.parse(localStorage.getItem(preferenceKey));
    if (!saved || typeof saved !== "object") return { ...defaults };
    return {
      format: saved.format === "24" ? "24" : "12",
      timeColor: colorValues[saved.timeColor] ? saved.timeColor : defaults.timeColor,
      dateColor: colorValues[saved.dateColor] ? saved.dateColor : defaults.dateColor,
      background: ["black", "dark-gray", "white-bg", "blue-bg"].includes(saved.background) ? saved.background : defaults.background
    };
  } catch {
    return { ...defaults };
  }
}

let preferences = loadPreferences();

function applyPreferences() {
  document.documentElement.style.setProperty("--time-color", colorValues[preferences.timeColor]);
  document.documentElement.style.setProperty("--date-color", colorValues[preferences.dateColor]);
  document.body.classList.remove("bg-dark-gray", "bg-white-bg", "bg-blue-bg");
  if (preferences.background !== "black") document.body.classList.add(`bg-${preferences.background}`);
  document.querySelector("#format").value = preferences.format;
  document.querySelector("#time-color").value = preferences.timeColor;
  document.querySelector("#date-color").value = preferences.dateColor;
  document.querySelector("#background").value = preferences.background;
}

function updateClock() {
  const now = new Date();
  const hour12 = preferences.format === "12";
  timeElement.dateTime = now.toISOString();
  timeElement.textContent = new Intl.DateTimeFormat(undefined, {
    hour: "2-digit", minute: "2-digit", second: "2-digit", hour12
  }).format(now);
  timeElement.setAttribute("aria-label", new Intl.DateTimeFormat(undefined, {
    hour: "numeric", minute: "2-digit", second: "2-digit", hour12
  }).format(now));
  dateElement.textContent = new Intl.DateTimeFormat(undefined, {
    weekday: "long", day: "numeric", month: "long", year: "numeric"
  }).format(now);
  timezoneElement.textContent = `YOUR LOCAL TIME · ${Intl.DateTimeFormat().resolvedOptions().timeZone || "LOCAL TIMEZONE"}`;
}

settingsButton.addEventListener("click", () => {
  const opening = settingsForm.hidden;
  settingsForm.hidden = !opening;
  settingsButton.setAttribute("aria-expanded", String(opening));
});

settingsForm.addEventListener("submit", (event) => {
  event.preventDefault();
  preferences = {
    format: document.querySelector("#format").value,
    timeColor: document.querySelector("#time-color").value,
    dateColor: document.querySelector("#date-color").value,
    background: document.querySelector("#background").value
  };
  try { localStorage.setItem(preferenceKey, JSON.stringify(preferences)); } catch { /* Storage may be disabled; clock still works. */ }
  applyPreferences();
  updateClock();
  settingsForm.hidden = true;
  settingsButton.setAttribute("aria-expanded", "false");
});

document.querySelector("#reset").addEventListener("click", () => {
  preferences = { ...defaults };
  try { localStorage.removeItem(preferenceKey); } catch { /* Clock still works without storage. */ }
  applyPreferences();
  updateClock();
  settingsForm.hidden = true;
  settingsButton.setAttribute("aria-expanded", "false");
});

applyPreferences();
updateClock();
window.setInterval(updateClock, 1000);
