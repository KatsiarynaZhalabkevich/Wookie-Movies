## Java - Wookie Movies

### Description

You are the owner of a movie theater in **Thikkiiana City,** on the Wookiee homeworld of Kashyyyk. Your customers are bored with the never changing selection and are asking for something completely different - they want to see what's playing on Earth. Wookies are the main exporter of Computer Technology for the New Republic so naturally you roll up your sleeves and get to work. You quickly scribble down some notes and after a few hours of relentless work you have a design in mind.

### Tasks

- Implement your backend API with Java (19) and Spring (Boot) so that the API can be accessed on http://localhost:3001
- Implement the following endpoints:
    - http://localhost:3001/movies: returns all movies in `movies.json`
    - http://localhost:3001/movies/:slug: returns the movie in `movies.json` with the corresponding `slug` or a 404 error if the slug is not found
    - http://localhost:3001/movies?q=${search_term}: returns all movies in `movies.json` where `search_term` is contained in the `title` (case insensitive)
    - http://localhost:3001/static/[backdrops|posters]/${file_name}: returns the images (according to `movies.json`)
- Only allow authorized request with the header `Authorization: Bearer Wookie2019`

### Bonus Tasks

- Use of a Database (SQLite, MySQL, PostgreSQL, ...) instead of `movies.json`
- Testing
