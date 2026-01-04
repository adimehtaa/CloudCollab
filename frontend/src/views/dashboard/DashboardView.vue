<template>
    <div class="min-h-screen bg-gray-100">
        <nav class="bg-white shadow-sm">
            <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                <div class="flex justify-between h-16">
                    <div class="flex items-center">
                        <h1 class="text-xl font-bold text-gray-900">CloudCollab</h1>
                    </div>

                    <div class="flex items-center space-x-4">
                        <span class="text-sm text-gray-700">
                            <!-- Welcome, {{ authStore.currentUser?.firstName }}! -->
                            Welcome
                        </span>
                        <button @click="handleLogout" class="btn-secondary text-sm">
                            Logout
                        </button>
                    </div>
                </div>
            </div>
        </nav>

        <main class="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
            <div class="px-4 py-6 sm:px-0">
                <div class="card">
                    <h2 class="text-2xl font-bold text-gray-900 mb-4">Dashboard</h2>

                    <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
                        <div class="bg-primary-50 p-6 rounded-lg">
                            <h3 class="text-lg font-semibold text-primary-900">Projects</h3>
                            <p class="text-3xl font-bold text-primary-600 mt-2">0</p>
                        </div>

                        <div class="bg-green-50 p-6 rounded-lg">
                            <h3 class="text-lg font-semibold text-green-900">Tasks</h3>
                            <p class="text-3xl font-bold text-green-600 mt-2">0</p>
                        </div>

                        <div class="bg-purple-50 p-6 rounded-lg">
                            <h3 class="text-lg font-semibold text-purple-900">Team Members</h3>
                            <p class="text-3xl font-bold text-purple-600 mt-2">1</p>
                        </div>
                    </div>

                    <div class="bg-gray-50 p-6 rounded-lg">
                        <h3 class="text-lg font-semibold text-gray-900 mb-2">User Information</h3>
                        <dl class="space-y-2">
                            <div>
                                <dt class="text-sm font-medium text-gray-500">Username</dt>
                                <dd class="text-sm text-gray-900">{{ authStore.currentUser?.username }}</dd>
                            </div>
                            <div>
                                <dt class="text-sm font-medium text-gray-500">Email</dt>
                                <dd class="text-sm text-gray-900">{{ authStore.currentUser?.email }}</dd>
                            </div>
                            <div>
                                <dt class="text-sm font-medium text-gray-500">Organization</dt>
                                <dd class="text-sm text-gray-900">
                                    {{ authStore.currentUser?.organizationName || 'No organization' }}
                                </dd>
                            </div>
                            <div>
                                <dt class="text-sm font-medium text-gray-500">Roles</dt>
                                <dd class="text-sm text-gray-900">
                                    <span v-for="role in authStore.userRoles" :key="role"
                                        class="inline-block bg-primary-100 text-primary-800 px-2 py-1 rounded text-xs mr-2">
                                        {{ role.replace('ROLE_', '') }}
                                    </span>
                                </dd>
                            </div>
                        </dl>
                    </div>
                </div>
            </div>
        </main>
    </div>

</template>

<script lang="ts" setup>


async function handleLogout() {

}

</script>