"""
This little script will try to parse a logcat file from your clipboard into HTML and open it
in the browser. This is useful when the backend throws an error and OkHttp prints it in console
"""

from tkinter import Tk
import webbrowser
import tempfile
import pathlib

logcat = Tk().clipboard_get()
stripped = []
seen_start = False
for line in logcat.split('\n'):
    try:
        content = line.split("Client:")[1]
        if content.strip() == "<!DOCTYPE html>":
            seen_start = True
        if seen_start:
            stripped.append(content)
        if content.strip() == "</html>":
            break
    except:
        continue

with tempfile.NamedTemporaryFile('w', delete=False, suffix='.html') as f:
    url = pathlib.Path(f.name).as_uri()
    f.write('\n'.join(stripped))
    print(url)
    webbrowser.open(url)


