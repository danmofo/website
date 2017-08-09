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
  constructor (endpoint = '/management/post/') {
    this.endpoint = endpoint;
  }

  add (post) {
    console.log('Adding post', post);
    console.log(document.cookie);

    fetch(this.endpoint + 'new', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkYW5tb2ZvIn0.8LvUyPdxtFexVEuNLJoG45ht3rkuF5oFmLFLIcoleW7CwwECdLTFZbD7CpUQdPupHDS7Dr8Q45pVLtHWz10KRg'
      },
      body: JSON.stringify(post)
    }).then(function(response) {
      console.log(response)
    });

  }

}

let service = new PostApiService()

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

service.add(post);

export default {
  name: 'create-post-form',
  data () {
    return {}
  }
}
</script>

<style>

</style>
