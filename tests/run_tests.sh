sh cleandbs.sh

mkdir dbs  # necessary for Descriptors
mkdir temp # necessary for Videos
mkdir videos_tests

echo 'Running C++ tests...'
./../build/tests/unit_test \
    --gtest_filter=-Descriptors_Add.add_flatl2_100d_2add:Descriptors_Store.add_ivfflatl2_100d_2add_file:ImageTest.CreateNameTDB:ImageTest.NoMetadata:PMGDQueryHandler.queryUpdateConstraintTest:PMGDQueryHandler.queryEdgeTestList:PMGDQueryHandler.queryEdgeTestSortList:PMGDQueryHandler.queryNodeEdgeTestList:QueryHandler.AddAndFind:VideoTest.CreateUnique
gcovr -k --root /vdms \
    -e /vdms/tests \
    -e /vdms/src/pmgd \
    -e /vdms/build \
    --exclude-unreachable-branches \
    --print-summary --xml-pretty -o c_coverage_report.xml


# echo 'Running Python tests...'
# cd python
# sh run_python_tests.sh
