<template>
  <div id="app">
    <create-post-form></create-post-form>
  </div>
</template>

<script>
import CreatePostForm from './components/post/CreatePostForm.vue'

export default {
  name: 'app',
  components: {
    'create-post-form': CreatePostForm
  },
  data () {
    return {

    }
  }
}
</script>

<style>
</style>
