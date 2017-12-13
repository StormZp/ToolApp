package com.storm.toolapp.base

import `in`.srain.cube.views.ptr.PtrClassicDefaultHeader
import `in`.srain.cube.views.ptr.PtrFrameLayout
import `in`.srain.cube.views.ptr.PtrHandler
import `in`.srain.cube.views.ptr.util.PtrLocalDisplay
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.alibaba.android.vlayout.layout.ScrollFixLayoutHelper
import com.storm.toolapp.R
import com.storm.toolapp.adapter.VLayoutAdapter
import com.storm.toolapp.helper.VLayoutHelper


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [BaseVlayoutFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [BaseVlayoutFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
open class BaseVlayoutFragment : Fragment() {
    lateinit var windowManager: WindowManager
    var isFirstRecycler = true
    var offsetX = 0
    var page = 1
    var loading = false
    open var isDownRefresh = true
    lateinit var viewPool: RecyclerView.RecycledViewPool
    val adapterList = ArrayList<DelegateAdapter.Adapter<out RecyclerView.ViewHolder>>()
    lateinit var adapter: DelegateAdapter
    lateinit var recycler: RecyclerView

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    private lateinit var lastItemAdapter: VLayoutAdapter

    fun setAlpha(float: Float) {
        val wl = activity.window.attributes
        wl.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        wl.alpha = float
        activity.window.setAttributes(wl)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }
    }

    open fun init() {
        windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val vlayout = VirtualLayoutManager(context)
        recycler.layoutManager = vlayout as RecyclerView.LayoutManager?
        adapter = DelegateAdapter(vlayout, true)
        recycler.adapter = adapter
        viewPool = RecyclerView.RecycledViewPool()
        recycler.setRecycledViewPool(viewPool)
        lastItemAdapter = object : VLayoutHelper.Builder() {}.
                setContext(context).
                setCount(1).
                setLayoutHelper(LinearLayoutHelper()).
                setViewType(1).
                setRes(R.layout.item_last).
                setParams(ViewGroup.LayoutParams(activity.windowManager.defaultDisplay.width, activity.windowManager.defaultDisplay.height / 10)).
                setOnBindView({ itemView, position ->
                    // val imageView: SimpleDraweeView = itemView.findViewById(R.id.image) as SimpleDraweeView
                }).creatAdapter()
    }

    open fun addItemDecoration() {
        recycler.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater?.inflate(R.layout.fragment_pager_home, container, false) as View
        recycler = view!!.findViewById(R.id.recycler) as RecyclerView
        init()
        initDownRefresh(view)
        setScollListen(view)
        setVLayout()
        //addBackTopButton()
        return view
    }

    var isBackTopShow = false
    var backTop: VLayoutAdapter? = null
    // TODO: Rename method, update argument and hook method into UI event
    fun addBackTopButton() {
        val windowManager = activity.windowManager
        val mLayoutParams = ViewGroup.LayoutParams(windowManager.defaultDisplay.height / 12, windowManager.defaultDisplay.height / 12)
        val mScrollFixLayoutHelper = ScrollFixLayoutHelper(ScrollFixLayoutHelper.BOTTOM_RIGHT, 12, windowManager.defaultDisplay.height / 13)
        mScrollFixLayoutHelper.setItemCount(1)
        mScrollFixLayoutHelper.showType = ScrollFixLayoutHelper.SHOW_ALWAYS
        if (backTop == null) {
            backTop = object : VLayoutHelper.Builder() {}.
                    setContext(context).
                    setLayoutHelper(mScrollFixLayoutHelper).
                    setParams(mLayoutParams).
                    setViewType(0).
                    setCount(1).
                    setRes(R.layout.button_vlayout).setOnBindView({ itemView, position ->
                val button = itemView.itemView.findViewById(R.id.back_top)
                button.setOnClickListener { gotoFirstItem() }
            }).creatAdapter()
        }
        if (!isBackTopShow) {
            adapter.addAdapter(backTop)
            isBackTopShow = true
        }
    }

    fun removeBackTopButton() {
        if (backTop != null) {
            adapter.removeAdapter(backTop)
            isBackTopShow = false
        }
    }

    open var isBackTop = true
    open fun scrollToDo() {
        if (isBackTop) {
            if (offsetX > windowManager.defaultDisplay.height) {
                addBackTopButton()
            } else {
                removeBackTopButton()
            }
        }
    }

    fun initDownRefresh(view: View) {
        val storeHousePtrFrame: PtrFrameLayout = view.findViewById(R.id.store_house_ptr_frame) as PtrFrameLayout
        storeHousePtrFrame.disableWhenHorizontalMove(true)
        storeHousePtrFrame.setPtrHandler(object : PtrHandler {
            override fun onRefreshBegin(frame: PtrFrameLayout) {
                frame.postDelayed({
                    adapter.removeAdapter(lastItemAdapter)
                    canLoadLastItem = true
                    loadOver = false
                    updataRecycle()
                    storeHousePtrFrame.refreshComplete()
                }, 1800)
            }

            override fun checkCanDoRefresh(frame: PtrFrameLayout, content: View, header: View): Boolean {
                // 默认实现，根据实际情况做改动
                return isFirstRecycler && isDownRefresh
                // return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header)
            }
        })
        val header = PtrClassicDefaultHeader(context)
        header.setPadding(0, PtrLocalDisplay.dp2px(15F), 0, 0)
        storeHousePtrFrame.headerView = header
        storeHousePtrFrame.addPtrUIHandler(header)
    }

    var lastItem = 0
    open var isShowLastItem = true
    open var canLoadLastItem = true
    fun setScollListen(view: View) {
        val recycler = view!!.findViewById(R.id.recycler) as RecyclerView
        recycler.setOnScrollListener(object : RecyclerView.OnScrollListener() {
            val isSlidingToLast = false
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                offsetX = offsetX + dy
                if (offsetX > 0) {
                    isFirstRecycler = false
                }
                scrollToDo()
                val manager = recyclerView!!.getLayoutManager() as LinearLayoutManager
                val lastVisibleItem = manager.findLastVisibleItemPosition()
                lastItem = lastVisibleItem
                val totalItemCount = manager.getItemCount()
                isFirstRecycler = manager.findFirstCompletelyVisibleItemPosition() == 0
                if (isFirstRecycler) {
                    offsetX = 0
                    //back_top.visibility = View.GONE
                }

                if (lastVisibleItem >= totalItemCount - 2 && dy > 0/*&& isSlidingToLast*/) {
                    //加载更多功能的代码
                    if (!loadMore() && canLoadLastItem && isShowLastItem) {
                        adapter.addAdapter(lastItemAdapter)
                        canLoadLastItem = false
                    }
                }
                if (lastVisibleItem <= totalItemCount - 3 /*&& dy > 0*/) {
                    if (!canLoadLastItem) {
                        Log.e("Page", "removeLastItem")
                        adapter.removeAdapter(lastItemAdapter)
                        canLoadLastItem = true
                    }

                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val manager = recyclerView!!.getLayoutManager() as LinearLayoutManager

                if (newState === RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition
                    isFirstRecycler = manager.findFirstCompletelyVisibleItemPosition() == 0
                    if (isFirstRecycler) {
                        offsetX = 0
                        removeBackTopButton()
                        //back_top.visibility = View.GONE
                    }
                    // 判断是否滚动到底部，并且是向右滚动

                }
            }
        })
    }

    open fun gotoFirstItem() {
        if (adapter.itemCount > 12)
            recycler.scrollToPosition(12)
        recycler.smoothScrollToPosition(0)

    }

    open fun loadOver() {
        loadOver = true
        recycler.smoothScrollBy(recycler.scrollX + 1, recycler.scrollY)
    }

    fun updataRecycle() {
        pullData(MODE_PULL)
    }

    fun loadMore(): Boolean {
        Log.e("Page", "LoadMore")
        if (!canLoadLastItem) {
            Log.e("PageLoadMore", "canLoadLastItem" + canLoadLastItem)
            return false
        }
        if (loading) {
            Log.e("PageLoadMore", "loading" + loading)

            return true
        }
        // Log.d("COUNT", (adapter.findAdapterByIndex(3) as VLayoutAdapter).mCount.toString())
        if (loadOver) {
            Log.e("PageLoadMore", "loadOver" + loadOver)

            return false
        } else {
            loading = true
            //  Log.e("pullData", arguments.getString("gc_id"))
            Log.e("PageLoadMore", "LoadMore")

            return pullData(MODE_LOADMORE)
        }
    }


    open fun setVLayout() {

    }

    var loadOver = false

    open fun pullData(mode: Int): Boolean {
        return false
    }

    override fun onDetach() {
        super.onDetach()
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"
        val MODE_PULL = 1
        val MODE_LOADMORE = 0
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.

         * @param param1 Parameter 1.
         * *
         * @param param2 Parameter 2.
         * *
         * @return A new instance of fragment BaseVlayoutFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): BaseVlayoutFragment {
            val fragment = BaseVlayoutFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
