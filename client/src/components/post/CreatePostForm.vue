<template>
  <div>
    <div class="page-header">
      <div class="container">
        <h2 class="page-header__title">Add new post</h2>
      </div>
    </div>
    <div class="container">
      <form>
        <div class="row">
          <div class="columns seven">

            <div class="row">
              <div class="six columns">
                <label for="exampleEmailInput">Title</label>
                <input class="u-full-width" type="text" placeholder="My cool post" id="exampleEmailInput" />
              </div>
              <div class="six columns">
                <label for="exampleRecipientInput">Permalink</label>
                <input class="u-full-width" type="text" placeholder="my-cool-post" id="exampleRecipientInput" />
              </div>
            </div>
            <label for="exampleMessage">Content</label>
            <span class="input-help">Posts are written in Markdown format.</span>
            <textarea class="u-full-width" placeholder="My super great post." id="exampleMessage"></textarea>

            <input class="button-primary" type="submit" value="Submit">
          </div>
          <div class="columns five">
            <p>Add content revisions section here.</p>
          </div>
        </div>
      </form>
    </div>
  </div>
</template>

<script>

function random(max = 100) {
  return Math.floor(Math.random() * max);
}

import 'whatwg-fetch'

class PostApiService {
  static add (post) {
    console.log('Adding post', post);

    return postAsJsonWithBody('/management/post/new', post).then(response => {
      // todo: parse response
      return response;
    });
  }
}

function postAsJsonWithBody(url, requestBody) {
    if(!url) throw Error('No url provided.');

    return fetch(url, {
      method: 'POST',
      credentials: 'same-origin',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(requestBody)
    });
}

let post = {
  "title": "My first post",
  "permalink": "my-first-post" + random(1000000),
  "author": {
    "name": "Daniel Moffat"
  },
  "tags": [
    {
      "value": "I don't exist lmaodsd"
    },
    {
      "value": "Sharable22"
    }
  ],
  "content": "# Title \n Some content"
}

PostApiService.add(post);

export default {
  name: 'create-post-form',
  data () {
    return {}
  }
}
</script>

<style>

</style>
