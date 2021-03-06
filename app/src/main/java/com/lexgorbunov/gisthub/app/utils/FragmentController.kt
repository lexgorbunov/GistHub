package com.lexgorbunov.gisthub.app.utils

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.View
import com.lexgorbunov.gisthub.R
import javax.inject.Inject
import javax.inject.Singleton

/**
 * For restore a fragment state when using the backStack
 * How to use:
 *   Add this lines to your fragment:
 *
 * 1. Add fragmentController as field of your fragment
 *   @Inject
 *   protected lateinit var fragmentController: FragmentController
 *
 * 2. Use `fragmentController.getState(savedInstanceState, this)` instead of savedInstanceState.
 * In yourFragmentInstance#onCreateView callback for example
 *   val savedState = fragmentController.getState(savedInstanceState, this)
 *
 * 3. Override yourFragmentInstance#onSaveInstanceState callback like this:
 * override fun onSaveInstanceState(outState: Bundle) {
 *     super.onSaveInstanceState(
 *         fragmentController.saveState(outState, this, view) {
 *             ...
 *             it.putInt(YOUR_KEY, your_value)
 *             ...
 *         }
 *     )
 * }
 */
@Singleton
class FragmentController @Inject constructor() {

    private val states: MutableMap<String, Bundle> = mutableMapOf()

    /**
     * Will set a fragment to a fragment container
     * @param fragmentManager
     * @param fragment Your fragment instance
     * @param withBackStack Put this change transaction to the backStack if value is true
     * @param saveState Save a state of a fragment if value is true
     * @param clearBackStack Clear BackStack if value is true
     * @param reuse Keeps the instance of a fragment that will replaced in fragmentManager if value is true,
     *              or recreate it when will need if value is false
     */
    fun setFragment(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        withBackStack: Boolean,
        saveState: Boolean,
        clearBackStack: Boolean = false,
        stackTag: String? = null,
        reuse: Boolean = false
    ) {

        val transaction = fragmentManager.beginTransaction()
        val tag = fragment.tag ?: fragment.javaClass.canonicalName

        if (clearBackStack) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            transaction.replace(R.id.fragment_container, fragment, tag)
        } else {
            val oldFrag = fragmentManager.primaryNavigationFragment
            if (oldFrag != null) {
                if (saveState) {
                    saveStateInternal(oldFrag)
                }
                if (reuse) {
                    transaction.detach(oldFrag)
                } else {
                    transaction.remove(oldFrag)
                }
                if (fragment.isDetached) {
                    transaction.attach(fragment)
                } else {
                    transaction.add(R.id.fragment_container, fragment, tag)
                }
            } else {
                transaction.replace(R.id.fragment_container, fragment, tag)
            }
        }
        if (withBackStack) {
            transaction.addToBackStack(stackTag)
        }
        transaction.setPrimaryNavigationFragment(fragment)
        transaction.commit()
    }

    /**
     * Will set a fragment to a fragment container
     * @param fragmentManager
     * @param fragmentTag Tag to lookup fragment in the fragmentManager
     * @param withBackStack Put this change transaction to the backStack if value is true
     * @param saveState Save a state of a fragment if value is true
     * @param reuse Keeps the instance of a fragment that will replaced in fragmentManager if value is true,
     *              or recreate it when will need if value is false
     * @param fragmentFactory Lambda with logic of create a fragment instance
     */
    fun setFragment(
        fragmentManager: FragmentManager,
        fragmentTag: String,
        withBackStack: Boolean,
        saveState: Boolean,
        clearBackStack: Boolean = false,
        stackTag: String? = null,
        reuse: Boolean = false,
        fragmentFactory: (() -> Fragment)
    ) {
        setFragment(
            fragmentManager = fragmentManager,
            fragment = fragmentManager.findFragmentByTag(fragmentTag) ?: fragmentFactory.invoke(),
            withBackStack = withBackStack,
            saveState = saveState,
            clearBackStack = clearBackStack,
            stackTag = stackTag,
            reuse = reuse
        )
    }

    private fun saveStateInternal(fragment: Fragment) {
        val state = Bundle()
        fragment.onSaveInstanceState(state)
        if (!state.isEmpty) {
            states[fragment.tag ?: fragment.javaClass.canonicalName] = state
        }
    }

    /**
     * Will save state of your fragment then return bundle with saved state
     * @param outState Source bundle, from your fragment callback #onSaveInstanceState(outState: Bundle) for example
     * @param fragment Your fragment instance
     * @param fragmentView Your fragment view or null. It need this for control when should skip saving of state
     * @param saveBlock Block with a saving state logic. You should use `it` argument as bundle for saving of state
     * @return Bundle with saved state
     */
    inline fun saveState(outState: Bundle, fragment: Fragment, fragmentView: View?, saveBlock: (bundle: Bundle) -> Unit): Bundle {
        if (fragmentView == null) {
            getState(null, fragment)?.let {
                outState.putAll(it)
            }
            return outState
        }
        saveBlock.invoke(outState)
        return outState
    }

    /**
     * Getting a state from the states storage
     * For example: Use it in #onViewCreated(view: View, savedInstanceState: Bundle?) of your fragment
     * @param savedInstanceState savedInstanceState from fragment callback
     * @param fragment Your fragment instance
     * @return Bundle with saved state
     */
    fun getState(savedInstanceState: Bundle?, fragment: Fragment): Bundle? {
        return savedInstanceState ?: states[fragment.tag ?: fragment::class.java.canonicalName]
    }
}
