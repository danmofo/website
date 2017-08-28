<template>
  <div>
    <p>Form goes here...</p>
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
